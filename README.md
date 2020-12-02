# twitter-module
This is a multi module project with two modules
The first module is an application to fetch tweets from twitter's filtered API and populate it to a SOLR collection

The second module is a REST based web service to show these fetched tweets sorted by the time of creation of the tweet is descending order

## Module 1
### twitter-data-populator
On running the twitter-data-populator-0.0.1.jar, the appication connects to the Filtered Stream API of twitter and starts fetching tweets based on the accounts, hastags which are followed for that token owner. On starting the accounts to be followed can be specified as VM arguments 

For fetching tweets tweeted from certain users: `-Daccounts=username1,username2`

For fetching tweets based on hashTags : `-Dtags=HashTag1,HashTag2` without adding # in front

For storing Tweets Apache Solr is being used (https://www.apache.org/dyn/closer.lua/lucene/solr/7.7.3/solr-7.7.3.zip). Before starting the application Solr should be up and a collection named "tweets" is to be created.

The bearer token for the developer account is to be placed in the application.yml file in place of the key twitter.bearerToken

## Module 2
### tweet-viewing-service
On running the main method in the tweet-viewing-service-0.0.1.jar, a REST based web service is started on port 8282, which exposes 4 APIs, the request and response details are mentioned in the apiDocs.html file

There is fetchTweets GET method for fetching the tweets which requires 2 int values as request params page_num and no_of_records, in order fetch the latest 20 tweets the page_num should be 0 and no_of_records should be 20. For the next 20 the page_num should be 0 and no_of_records should be the same. This way the records can be paged based on any threshold value for number of records

Further there is GET,POST,DELETE methods to fetch rules for the accounts/hashtags currently being followed which would contain matchingRuleId which can be passed as list to delete method which would then stop fetching the tweets based on that id and on refresh those tweets will no longer appear for the /tweets GET method.
The POST method takes in a list of accounts, hashtags to configure new accounts/hashtags to follow

The bearer token for the developer account is to be placed in the application.yml file in place of the key twitter.bearerToken

#### Code Coverage
The code coverage has been checked using Jacoco plugin. Whose report can be seen inside /target/site/jacoco/index.html for both the modules
