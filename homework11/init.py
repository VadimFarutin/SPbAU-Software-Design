import boto3
from time import sleep

sleep(10)
sqs = boto3.resource('sqs', endpoint_url='http://localstack:4576/')

queue = sqs.create_queue(QueueName="A")
queue = sqs.create_queue(QueueName="B")
queue.send_message(MessageBody="1")
