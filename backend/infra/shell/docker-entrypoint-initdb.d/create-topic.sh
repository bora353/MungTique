echo [create] review-result topic ~!
kafka-topics.sh --create --topic queuing-amd-transfer --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic db-review-so-save --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic queuing-review-adc-save --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic db-review-amd-save --bootstrap-server kafka-3:9094
echo [create] image-collector topic ~!
kafka-topics.sh --create --topic queuing-fail-service --bootstrap-server kafka-3:9094
echo [create] navigate topic ~!
kafka-topics.sh --create --topic queuing-navigate-parse --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic queuing-adc-start --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic queuing-parse-restart --bootstrap-server kafka-3:9094
echo [create] parser topic ~!
kafka-topics.sh --create --topic apmi --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic adc --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic parser-producer-topic --bootstrap-server kafka-3:9094
echo [create] collector topic ~!
kafka-topics.sh --create --topic queuing-collector-reStart --bootstrap-server kafka-3:9094
echo [create] notification topic ~!
kafka-topics.sh --create --topic notification-killing-defect-mail --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic notification-weird-sensitivity-mail --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic notification-killing-defect-slack --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic notification-weird-sensitivity-slack --bootstrap-server kafka-3:9094
kafka-topics.sh --create --topic notification-job-status --bootstrap-server kafka-3:9094
echo [create] collector + image-collector + watcher common topic ~!
kafka-topics.sh --create --topic queuing-collector-start --bootstrap-server kafka-3:9094
echo [create] collector + navigate topic ~!
kafka-topics.sh --create --topic queuing-parse-start --bootstrap-server kafka-3:9094
echo [create] image-collector + navigate topic ~!
kafka-topics.sh --create --topic queuing-navigate-adc --bootstrap-server kafka-3:9094
echo [create] review-result + process topic ~!
kafka-topics.sh --create --topic queuing-process-status --bootstrap-server kafka-3:9094