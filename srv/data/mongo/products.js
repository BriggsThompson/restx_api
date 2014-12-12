db.createCollection("products")

db.products.ensureIndex( { "_id" : 1}, { background: true, unique: true } )
db.products.ensureIndex( { "userKey" : 1, "title": 1 }, { background: true, unique: true } )
db.products.ensureIndex( { "created" : 1}, { background: true } )
db.products.ensureIndex( { "lastUpdated" : 1}, { background: true } )