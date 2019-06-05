import boto3
from time import sleep

sleep(10)
sqs = boto3.resource('sqs', endpoint_url='http://localstack:4576/')

queue = sqs.create_queue(QueueName="A", Attributes={'DelaySeconds': '3'})
queue = sqs.create_queue(QueueName="B", Attributes={'DelaySeconds': '3'})
queue.send_message(MessageBody="1")
