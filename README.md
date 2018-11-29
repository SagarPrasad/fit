## How to search into schema-less JSON with DSE?

The idea is to flatten JSON keys and put keys/values in a dynamic map field through a Field Input Transformer.

If you need different analyzers, you can add several dynamic field in the schema.xml.

### Field input/output (FIT) transformer API

This is an example of https://docs.datastax.com/en/dse/5.1/dse-dev/datastax_enterprise/search/fieldInputOutputTransformerApi.html


CREATE KEYSPACE fit WITH replication = {'class': 'SimpleStrategy' , 'replication_factor': 1 };
 

CREATE TABLE fit.jsonlist (
    id text PRIMARY KEY,
    complexvalue text
);

You need to download and unzip dse and set path in maven of 3 jars

mvn clean install

copy target/fit-1.0-SNAPSHOT.jar to install-location/resources/solr/lib 
    
in my laptop

cp target/fit-1.0-SNAPSHOT.jar /home/florent/soft/dse-5.1.6/resources/solr/lib/

restart node

dsetool create_core fit.jsonlist schema=schemaList.xml solrconfig=configList.xml

INSERT INTO fit.jsonlist (id, complexvalue) VALUES ('test2','{"name":"Mortgage","amount":"100000","duration":"60","garantor":{"partyId1":"abc123","partyId2":"def456"}}');

select * from fit.jsonlist where solr_query='map_garantor.partyId1:abc123' ;

 id    | complexvalue                                                                                               | solr_query
-------+------------------------------------------------------------------------------------------------------------+------------
 test2 | {"name":"Mortgage","amount":"100000","duration":"60","garantor":{"partyId1":"abc123","partyId2":"def456"}} |       null


INSERT INTO fit.jsonlist (id, complexvalue) VALUES ('test','{"name":"John","age":30,"cars":["Ford","BMW","Fiat Panda"]}');

select * from fit.jsonlist where solr_query='map_cars_:ford' ;

 id   | complexvalue                                          | solr_query
------+-------------------------------------------------------+------------
 test | {"name":"John","age":30,"cars":["Ford","BMW","Fiat Panda"]} |       null



select * from fit.jsonlist where solr_query='map_cars_:"ford bmw"' ;

 id | complexvalue | solr_query
----+--------------+------------

(0 rows)    // as expected


select * from fit.jsonlist where solr_query='map_cars_:"fiat panda"' ; 

 id   | complexvalue                                                | solr_query
------+-------------------------------------------------------------+------------
 test | {"name":"John","age":30,"cars":["Ford","BMW","Fiat Panda"]} |       null


---------------------------------------

# Testing for the C* Hooks



docker exec -it dcp-dse nodetool tablestats dse_system_local
dsetool -h localhost create_core dcp_test.simple_table  schema=schemaList.xml solrconfig=configList.xml 


dsetool -h localhost create_core dcp_test.simple_table  generateResources=true reindex=true

dsetool -h localhost unload_core dcp_test.simple_table


CREATE SEARCH INDEX ON demo.health_data;
docker-compose \
-f docker-compose.yml \
up -d --scale node=2




##DDL/DML
CREATE KEYSPACE dcp_test WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TYPE IF NOT EXISTS dcp_test.sampleType (
	description text,
	type_name text
);

CREATE TABLE dcp_test.simple_table (
name text,
color text,
created_date timestamp,
custom_types frozen<sampleType>,
tags list<text>,
attributes map<text, text>,
primary key (name)
);



insert into dcp_test.simple_table (name, color, created_date, custom_types, "attributes", tags) values ('Sagar', 'Black', '2018-02-21 08:06:34', {description: 'test', type_name: 'test-tyep'}, {'A':'B'}, ['L1','L2']) 
insert into dcp_test.simple_table (name, color, created_date, custom_types, "attributes", tags) values ('Prasad', 'Red', '2018-02-21 08:06:34', {description: 'b', type_name: 'test-type'}, {'C':'D'}, ['C1','C2']) 

select * from dcp_test.simple_table
alter table dcp_test.simple_table add jsonField text;
update dcp_test.simple_table set newcol='kkkk' where name='Prasad'
update dcp_test.simple_table set jsonField='{"state":"SUBMITTED","id":"1f0dc790-92db-11e8-97c3-2f8d77b0057d","number":"2018209581258707","submittedDate":1532835414744,"bag":{"itemCount":3,"items":[{"quantity":1,"product":{"id":"ppr5007397341","number":"0386965","type":"REGULAR","brandName":"ARIZONA","categoryId":"cat100240063","division":"007","subdivision":"038","entity":"17","digital":false,"monogram":false,"commodityCode":"531116","factoryShip":false,"fsVendor":"764605","itemRestricted":false,"programTypeCode":"0","factoryShipLeadTimeQty":"0","factoryShipLeadTimeUOM":"DAYS","isRecycleFeeEligible":false,"isDisposalFeeEligible":false,"marketingLabel":"LIMITED TIME SPECIAL!","truckable":false,"upsCode":"1","mailCode":"1","willCallStoreIndicator":false,"sephora":false,"salonItem":false,"fineJewelry":false,"oversized":false},"sku":{"id":"03869650075","quantity":1},"inventory":{"status":"INSTOCK","quantity":0,"urgency":false},"prices":[{"type":"ORIGINAL","amount":37},{"type":"SALE","amount":11.1,"label":"ACTIVE"}],"activePriceType":"SALE","isExchangeShipping":false,"isShippingThresholdExcluded":false,"lastModifiedDate":1532835237651,"bundleDiscountApplied":false}]},"adjustments":[{"code":"COUPON","subType":"PROMOTIONAL","restrictionType":"UNRESTRICTED","value":"4TOSHOP","amount":30,"status":"PARTIALLYDENIED","reason":"84","paymentType":"NON_RESTRICTED"}],"delivery":{"groups":[{"type":"HOME","address":{"city":"MIAMI","state":"FL","zip":"33179","country":"US","standardizedValue":"0","zipExtension":"3034","commercial":false,"primeDistributionCenterId":"03","directShipStoreNum":"2071","stateBit":"9","poBox":false},"serviceLevels":{"data":[{"code":"STANDARD","promises":[{"type":"INSTOCK"},{"type":"MANUFACTURSHIP"}],"selected":true,"holidayShipping":false,"duration":"Standard delivery","charge":8.95}]}}]},"billing":{"creditCards":{"cards":[{"type":"PLCC","lastFour":"0727","status":"UNVERIFIED","cvvResult":"N","avsResult":"Y","address":{"state":"FL","zip":"54204","addressVid":"20151362034"},"encryptionType":"primary","approvalCode":"028051","amount":95.71,"requestedAmount":95.71,"responseCode":"00","authType":"FULL_AUTH"}]}},"charity":{"selected":false},"totals":[{"amount":95.71}],"charges":[{"amount":5.68},{"amount":8.95}],"isPriceAccurate":true,"relationships":{"account":{"id":"zZmorzNw0t00ydh-oXRl"}},"clientIpAddress":"108.245.254.156","orderSource":"NREG","intialEntryChannel":"INET","finalEntryChannel":"RWDT","isTestOrder":false}' where name='new'


select * from dcp_test.simple_table
