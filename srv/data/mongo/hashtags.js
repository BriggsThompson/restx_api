db.createCollection("hashtags")

db.hashtags.ensureIndex( { "_id" : 1 }, { background: true, unique: true } )
db.hashtags.ensureIndex( { "tag" : 1 }, { background: true } )
db.hashtags.ensureIndex( { "userKey" : 1 }, { background: true } )
db.hashtags.ensureIndex( { "created" : 1 }, { background: true } )
db.hashtags.ensureIndex( { "lastUpdated" : 1}, { background: true } )