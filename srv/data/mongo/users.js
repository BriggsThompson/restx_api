db.createCollection("users")

db.users.ensureIndex( { "email" : 1}, { background: true, unique: true } )
db.users.ensureIndex( { "facebookId" : 1}, { background: true, unique: true, sparse: true } )
db.users.ensureIndex( { "created" : 1}, { background: true } )
db.users.ensureIndex( { "lastUpdated" : 1}, { background: true } )