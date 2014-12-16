db.createCollection("images")

db.images.ensureIndex( { "_id" : 1}, { background: true, unique: true } )
db.images.ensureIndex( { "userKey" : 1 }, { background: true } )
db.images.ensureIndex( { "productKey" : 1 }, { background: true } )
db.images.ensureIndex( { "created" : 1}, { background: true } )
db.images.ensureIndex( { "lastUpdated" : 1}, { background: true } )