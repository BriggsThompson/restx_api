api
===

*Hazitt API*

You must have mongodb running locally. 

DB name: *hazitt* 

Creation Scripts: srv/data/mongo/

The admin_users_creation.js is necessary to login since persistance layer is mongo. 

Modify /etc/hosts (necessary for facebook auth, you may also need to add your public IP to facebook)

```
127.0.0.1     local.hazitt.com
```

Uses RestX  http://restx.io/

```
curl -Ls http://restx.io/install.sh | sh
```

```
cd api/srv
restx
shell install
deps install
app run
```





Go to http://local.hazitt.com:8080/api/@/ui/

API endpoints are documented http://local.hazitt.com:8080/api/@/ui/api-docs/

If testing non-admin authentication, remove cookies from admin.

