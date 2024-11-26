#ifndef NETWORKMANAGER_H
#define NETWORKMANAGER_H

#include <QObject>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QJsonObject>
#include <QTimer>
#include <QMap>
#include <QUrl>

// Enum to distinguish request types
enum class RequestType {
    GetExampleData,
    PostUserData,
    FetchStatistics,
    UpdateSettings,
    DeleteRecord,
    Custom // Add more as needed
};

class NetworkManager : public QObject
{
    Q_OBJECT

public:
    explicit NetworkManager(QObject *parent = nullptr);

    // Singleton instance
    static NetworkManager &instance();

    // HTTP Methods
    void get(RequestType requestType, const QUrl &url, int timeout = 5000);
    void post(RequestType requestType, const QUrl &url, const QJsonObject &payload, int timeout = 5000);
    void put(RequestType requestType, const QUrl &url, const QJsonObject &payload, int timeout = 5000);
    void del(RequestType requestType, const QUrl &url, int timeout = 5000);

signals:
    void requestCompleted(RequestType requestType, const QJsonObject &response);
    void requestFailed(RequestType requestType, const QString &error);
    void requestTimeout(RequestType requestType, const QString &error);

private slots:
    void onReplyFinished(QNetworkReply *reply);
    void onRequestTimeout(QNetworkReply *reply);

private:
    QNetworkAccessManager *m_networkAccessManager;

    struct RequestInfo {
        QTimer *timer;
        QNetworkReply *reply;
        RequestType requestType;
    };

    QMap<QNetworkReply *, RequestInfo> m_activeRequests;

    void sendRequest(RequestType requestType, const QUrl &url, const QByteArray &data, QNetworkAccessManager::Operation operation, int timeout);
};

#endif // NETWORKMANAGER_H
