db.createCollection("userCredentials")

db.userCredentials.ensureIndex( { "_id" : 1}, { background: true, unique: true } )
db.userCredentials.ensureIndex( { "created" : 1}, { background: true } )
db.userCredentials.ensureIndex( { "lastUpdated" : 1}, { background: true } )