db.createCollection("userCredentials")

db.users.ensureIndex( { "_id" : 1}, { background: true, unique: true } )
db.users.ensureIndex( { "created" : 1}, { background: true } )
db.users.ensureIndex( { "lastUpdated" : 1}, { background: true } )