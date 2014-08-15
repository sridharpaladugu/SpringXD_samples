![alt text](https://github.com/sridharpaladugu/SpringXD_Pivotal_hadoop/blob/master/SpringXD_HAWQ_SEQFILE_DEMO/StreamingBinaryDataToHawq_files/image001.png "Logo")
#Streaming Binary data in HAWQ.
###
Pivotal offers HAWQ, SQL-on-Hadoop engine. HAWQ is designed as a MPP SQL processing engine optimized for analytics with full transaction support. HAWQ breaks complex queries into small tasks and distributes them to MPP query processing units for execution. The query planner, dynamic pipeline, leading edge interconnect, and the specific query executor optimization for distributed storage are designed to work seamlessly, to support highest level of performance and scalability. Based on Hadoop’s distributed storage, HAWQ has no single point of failure and supports fully automatic online recovery. System states are continuously monitored, therefore if a segment fails; it is automatically removed from the cluster. During this process, the system continues serving customer queries, and the segments can be added back to the system when necessary. To learn about HAWQ please visit [Pivotal HAWQ link](http://www.pivotal.io/big-data/pivotal-hd)

###
HAWQ PXF feature let us access information from HDFS and access the data in ANSI SQL on all the primitive data types. PXF can query data from support HBase, Hive, Sequence files, csv files stored in HDFS. Let us examine a scenario where we are streaming in a sequence file in HDFS and expose the data via HAWQ external table. 
To demonstrate the scenario Let us generate some sequence files, store them in HDFS and query the via HAWQ external table feature. The flow we are going to use is as below;

