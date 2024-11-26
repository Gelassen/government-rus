#include "NetworkManager.h"
#include <QJsonDocument>
#include <QJsonParseError>
#include <QDebug>

NetworkManager::NetworkManager(QObject *parent)
    : QObject(parent),
      m_networkAccessManager(new QNetworkAccessManager(this))
{
    connect(m_networkAccessManager, &QNetworkAccessManager::finished, this, &NetworkManager::onReplyFinished);
}

NetworkManager &NetworkManager::instance()
{
    static NetworkManager instance;
    return instance;
}

void NetworkManager::get(RequestType requestType, const QUrl &url, int timeout)
{
    sendRequest(requestType, url, QByteArray(), QNetworkAccessManager::GetOperation, timeout);
}

void NetworkManager::post(RequestType requestType, const QUrl &url, const QJsonObject &payload, int timeout)
{
    sendRequest(requestType, url, QJsonDocument(payload).toJson(), QNetworkAccessManager::PostOperation, timeout);
}

void NetworkManager::put(RequestType requestType, const QUrl &url, const QJsonObject &payload, int timeout)
{
    sendRequest(requestType, url, QJsonDocument(payload).toJson(), QNetworkAccessManager::PutOperation, timeout);
}

void NetworkManager::del(RequestType requestType, const QUrl &url, int timeout)
{
    sendRequest(requestType, url, QByteArray(), QNetworkAccessManager::DeleteOperation, timeout);
}

void NetworkManager::sendRequest(RequestType requestType, const QUrl &url, const QByteArray &data, QNetworkAccessManager::Operation operation, int timeout)
{
    QNetworkRequest request(url);
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");

    QNetworkReply *reply = nullptr;
    switch (operation) {
    case QNetworkAccessManager::GetOperation:
        reply = m_networkAccessManager->get(request);
        break;
    case QNetworkAccessManager::PostOperation:
        reply = m_networkAccessManager->post(request, data);
        break;
    case QNetworkAccessManager::PutOperation:
        reply = m_networkAccessManager->put(request, data);
        break;
    case QNetworkAccessManager::DeleteOperation:
        reply = m_networkAccessManager->deleteResource(request);
        break;
    default:
        qWarning() << "Unsupported operation!";
        return;
    }

    // Create and start a timeout timer for the request
    QTimer *timer = new QTimer(this);
    timer->setSingleShot(true);
    connect(timer, &QTimer::timeout, this, [this, reply]() {
        onRequestTimeout(reply);
    });
    timer->start(timeout);

    // Store the active request and its associated information
    m_activeRequests[reply] = {timer, reply, requestType};
}

void NetworkManager::onReplyFinished(QNetworkReply *reply)
{
    if (!m_activeRequests.contains(reply)) {
        reply->deleteLater();
        return;
    }

    RequestInfo info = m_activeRequests.take(reply);
    info.timer->stop();
    info.timer->deleteLater();

    if (reply->error() == QNetworkReply::NoError) {
        QJsonParseError parseError;
        QJsonDocument responseDoc = QJsonDocument::fromJson(reply->readAll(), &parseError);

        if (parseError.error == QJsonParseError::NoError) {
            emit requestCompleted(info.requestType, responseDoc.object());
        } else {
            emit requestFailed(info.requestType, "Failed to parse JSON response");
        }
    } else {
        emit requestFailed(info.requestType, reply->errorString());
    }

    reply->deleteLater();
}

void NetworkManager::onRequestTimeout(QNetworkReply *reply)
{
    if (!m_activeRequests.contains(reply)) {
        return;
    }

    RequestInfo info = m_activeRequests.take(reply);
    info.timer->deleteLater();

    if (reply->isRunning()) {
        reply->abort();
        reply->deleteLater();
        emit requestTimeout(info.requestType, "Request timed out");
    }
}
