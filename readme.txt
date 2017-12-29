XGen Readme

OVERVIEW 

XGen source code generator creates Java source code from a simple xml document. 
Its primary function is to generate JDBC compliant beans that allow object level 
persistence to relational databases. It has full support for all JDBC 2.0 datatypes, 
including BLOB and CLOB.

REQUIREMENTS

Required libraries are included in the lib directory. Check build.properties 
for any additional libraries required to build the distribution.
XGen uses the Jakarta Velocity template engine.

LICENSE

This program is distributed under GPL. See license.txt or www.gnu.org for the 
license. This software does not come with a warranty of any kind. Use at you own
risk. You may modify and distribute the code as you wish, as long as you provide 
the source code and this copyright with it. 

AUTHOR

Brad Matlack
matlackb@users.sourceforge.net
Copyright (c) Brad D Matlack 2-2003

CHANGE LOG

Build 1.0: Initial Release 2002-03-21

Build 1.1 2002-05-15
XGen is refactored into the Xml2Bean Module of XiT.

Build 0.2.0 2004-03-01
XGen is refactored into the Xml2Java Plugin of XiT.

Build 0.3.0 2004-03-10
XGen is refactored and re-released as a standalone app within workzen.xgen code tree.
Added VETexenTask

Build 0.3.1
Added associations: Reference and Collection to model

Build 0.3.2
Refactoring of model to specify Column and JavaClass

Build 0.3.3
Refactored TypeMap and schema to better handle primitive types.

Build 0.3.4-6
Added manager templates

Build 0.3.7
Bug fix in manager loadByWhere()

Build 0.3.8
Moved the java model into its own package "java"

Build 0.3.9
Moved the file and xml model into its own tree
Refactored XGenLoader to JavaModelLoader
Completed documentation of the Workzen release

Build 0.3.10
Minor scrub
Added Website Module, including WebsiteModelLoader and templates
Refactored XGenTask to accurately set the controlTemplate using a controlTemplate ant paramenter.
Updated XGenTask javadocs
 
Build 0.4.0
Major refactoring
XGen has removed the dependency on Ant and Velocity.
Two modules are required to run: ILoaders and IProcessors.
Properties are defined within the task and used to initalize both modules.
The loader loads a model, and the processor processes it.
Modules are self configuring, and will error if not correctly configured.
Control templates have been modifed accordingly.

Build 0.4.1  2007-08-06 BDM
Added deleteByWhere() to templates/persistence/manager/table.vm
Removed deprecated Enumeration usage to allow 1.5+ compiles
Added countRows() to templates/persistence/manager/table.vm
Fixed bug in templates/persistence/manager/table.vm store() function to display primitives correctly

Build 0.4.1 2007-09-11
Added JavaModelDbLoader to generate java class files from database schema.
Updated workzen.common to 0.6.7 to access support for DbMetaData

Build 0.4.2 2007-10-10
Modified model.java.SqlTypeMap to include common Postgresql mappings
Modified "numeric" type to Integer to allow compatability between Oracle and Postgresql Integer type

Build 0.4.3 2007-10-15
Modified JavaModelDbLoader to use Object identifiers (Long, Integer) instead 
of primitives to eliminate autoboxing problems (inserting keys of 0 instead of null)
Fixed bug in templates/persistence/manager/table.vm where Object keys (Integer) were not incrementing +1.
Fixed bug in templates/persistence/manager/table.vm where null objects caused jdbc errors.
Moved templates/persistence/bean to templates/persistence/legacy

Build 0.5.0 2007-11-1
Release of 4.3 into version 5.0. The build_java_from_db.xml will reverse engineer a database into persistable Java beans. This represents a different approach to application development. Instead of hand creating metadata, the database acts as the metadata. (This approach can be called Model 1.5) Updates to the database are always autonomous table updates. Selects, or views, are always joins, which can be efficiently achieved using SQL and interface widgets like DisplayTag.





