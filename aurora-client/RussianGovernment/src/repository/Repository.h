#ifndef REPOSITORY_H
#define REPOSITORY_H

#include <QObject>
#include <QJsonObject>
#include <QUrlQuery>
#include <functional>

#include "../network/NetworkManager.h"

class Repository : public QObject
{
    Q_OBJECT

public:
    explicit Repository(const QString &baseUrl, QObject *parent = nullptr);

    using SuccessCallback = std::function<void(const QJsonObject &)>;
    using FailureCallback = std::function<void(const QString &)>;

    void fetchBills(int userId, int timeout, const SuccessCallback &onSuccess, const FailureCallback &onFailure);

private:
    QString m_baseUrl;

    QUrl constructUrl(const QString &endpoint, const QString &extraPath = QString(), const QUrlQuery &queryParams = QUrlQuery()) const;

private slots:
    void handleRequestCompleted(RequestType requestType, const QJsonObject &response);
    void handleRequestFailed(RequestType requestType, const QString &error);

private:
    SuccessCallback m_successCallback;
    FailureCallback m_failureCallback;
};

#endif // REPOSITORY_H
