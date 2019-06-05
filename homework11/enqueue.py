import boto3
from time import sleep
import sys

sleep(20)
sqs = boto3.resource('sqs', endpoint_url='http://localstack:4576/')

from_queue_name = sys.argv[1]
to_queue_name = sys.argv[2]
from_queue = sqs.get_queue_by_name(QueueName=from_queue_name)
to_queue = sqs.get_queue_by_name(QueueName=to_queue_name)

while True:
    for message in from_queue.receive_messages():
        value = int(message.body)
        message.delete()
        print("%s received %d" % (from_queue_name, value), flush=True)
        to_queue.send_message(MessageBody=str(value + 1))
