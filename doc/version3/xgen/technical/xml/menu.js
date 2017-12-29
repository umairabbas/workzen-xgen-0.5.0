/* 
 * menu.js 
 * Brad Matlack 4-2004
 */

var project = "XGen";

var menu = new Menu(basepath,"Xml Schema");

//menu.addMenuItem("Schema","xgen/xml/schema.html");
menu.addMenuItem("Blob schema", "xgen/xml/blog_schema_1.html",menuicon);
menu.addMenuItem("Ant schema", "xgen/xml/build_blog.html",menuicon);

/* bar and tab inherited from nav */
print(bar, xgenTab, menu);
