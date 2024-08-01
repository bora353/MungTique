#echo [create] user topic ~!
#kafka-topics.sh --create --topic queuing-user-transfer --bootstrap-server kafka-3:9094
#kafka-topics.sh --create --topic db-review-so-save --bootstrap-server kafka-3:9094
#kafka-topics.sh --create --topic queuing-review-adc-save --bootstrap-server kafka-3:9094
#kafka-topics.sh --create --topic db-user-care-save --bootstrap-server kafka-3:9094
#echo [create] care topic ~!
#kafka-topics.sh --create --topic queuing-care-transfer --bootstrap-server kafka-3:9094
#kafka-topics.sh --create --topic db-review-so-save --bootstrap-server kafka-3:9094
#kafka-topics.sh --create --topic queuing-review-adc-save --bootstrap-server kafka-3:9094
#kafka-topics.sh --create --topic db-care-user-save --bootstrap-server kafka-3:9094