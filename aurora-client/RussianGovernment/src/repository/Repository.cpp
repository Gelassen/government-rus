#include "Repository.h"

Repository::Repository(const QString &baseUrl, QObject *parent)
    : QObject(parent), m_baseUrl(baseUrl)
{
    connect(&NetworkManager::instance(), &NetworkManager::requestCompleted, this, &Repository::handleRequestCompleted);
    connect(&NetworkManager::instance(), &NetworkManager::requestFailed, this, &Repository::handleRequestFailed);
}

void Repository::fetchBills(int userId, int timeout, const SuccessCallback &onSuccess, const FailureCallback &onFailure)
{
    m_successCallback = onSuccess;
    m_failureCallback = onFailure;

    QString endpoint = "/users";
    QString extraPath = QString("/%1/bills").arg(userId);
    QUrlQuery queryParams;
    queryParams.addQueryItem("status", "active");

    QUrl url = constructUrl(m_baseUrl, extraPath, queryParams);

    NetworkManager::instance().get(RequestType::FETCH_BILLS, url, timeout);
}

QUrl Repository::constructUrl(const QString &endpoint, const QString &extraPath, const QUrlQuery &queryParams) const
{
    QUrl url(m_baseUrl);
    url.setPath(endpoint + extraPath);
    url.setQuery(queryParams);
    return url;
}

void Repository::handleRequestCompleted(RequestType requestType, const QJsonObject &response)
{
    if (requestType == RequestType::FETCH_BILLS) {
        if (m_successCallback) {
            m_successCallback(response);
        }
    }
}

void Repository::handleRequestFailed(RequestType requestType, const QString &error)
{
    if (requestType == RequestType::FETCH_BILLS) {
        if (m_failureCallback) {
            m_failureCallback(error);
        }
    }
}
