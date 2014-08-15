![alt text](https://github.com/sridharpaladugu/SpringXD_Pivotal_hadoop/blob/master/SpringXD_HAWQ_SEQFILE_DEMO/StreamingBinaryDataToHawq_files/image001.png "Logo")
#Streaming Binary data in HAWQ.
###
Pivotal offers HAWQ, SQL-on-Hadoop engine. HAWQ is designed as a MPP SQL processing engine optimized for analytics with full transaction support. HAWQ breaks complex queries into small tasks and distributes them to MPP query processing units for execution. The query planner, dynamic pipeline, leading edge interconnect, and the specific query executor optimization for distributed storage are designed to work seamlessly, to support highest level of performance and scalability. Based on Hadoop’s distributed storage, HAWQ has no single point of failure and supports fully automatic online recovery. System states are continuously monitored, therefore if a segment fails; it is automatically removed from the cluster. During this process, the system continues serving customer queries, and the segments can be added back to the system when necessary. To learn about HAWQ please visit [Pivotal HAWQ link](http://www.pivotal.io/big-data/pivotal-hd).
HAWQ PXF feature let us access information from HDFS and access the data in ANSI SQL on all the primitive data types. PXF can query data from support HBase, Hive, Sequence files, csv files stored in HDFS. Let us examine a scenario where we are streaming in a sequence file in HDFS and expose the data via HAWQ external table. 
To demonstrate the scenario Let us generate some sequence files, store them in HDFS and query the via HAWQ external table feature. The flow we are going to use is as below;

####
The following figure shows the process flow.
![alt text](https://github.com/sridharpaladugu/SpringXD_Pivotal_hadoop/blob/master/SpringXD_HAWQ_SEQFILE_DEMO/StreamingBinaryDataToHawq_files/image004.png "Logo")

####As shown in the diagram the process is broken 3 steps; data producer, data ingestion, query step. 

We are going to use SpringXD for data ingestion. To setup SpringXD please refer to http://docs.spring.io/springxd/docs/1.0.0.RELEASE/reference/html/#_install_spring_xd to setup SpringXD in single node environment and configure HADOOP FS in SpringXD.

Flow 1, usually an external source sending the files to a file store. To simulate the flow, I choose to use SpringXD to trigger the Document Generator process for a fixed interval time; which generates a sequence file containing a record with three fields name, type, content.  The name and type are text type and content is a byte array representing the document. The file will be stored in a folder. The process is configured to accept the folder location and number of records would like to generate in a file.

Flow2 is another SpringXD stream which uses out of box File polling process and a custom Sink to store files to HDFS. This process polls the folder for fixed interval time and if there is any new file, moves to HDFS.
The final step is creating an external table in HAWQ to query the data in client Application.
The code for this sample can be downloaded at
