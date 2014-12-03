db.users.save(
    {
        "_id" : ObjectId("547e8cddf8cda528b842d6e8"),
        "email" : "admin@hazitt.com",
        "firstName" : "admin",
        "lastName" : "admin",
        "created" : ISODate("2014-11-30T02:43:21.822Z"),
        "lastUpdated" : ISODate("2014-11-30T02:43:21.822Z"),
        "roles" : [
            "admin",
            "restx-admin"
        ]
    })

db.userCredentials.save(
    {
        "_id" : ObjectId("547e8cddf8cda528b842d6e8"),
        "passwordHash" : "$2a$10$U6GoeoBUFVVWW0aKIOEcrOi1IWLqEV6VdrEqpaU1c94kNKVvRBA9W",
        "created" : ISODate("2014-11-29T04:24:49.357Z"),
        "lastUpdated" : ISODate("2014-11-29T04:24:49.357Z")
    })