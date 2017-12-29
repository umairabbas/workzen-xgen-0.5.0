/* 
 * code menu.js 
 * Brad Matlack 4-2004
 */

var project = "XGen";
var path = basepath + "/xgen/loaders/java";


var menu = new Menu(path,"Loaders");
menu.addMenuItem("up", "../index.html",upicon);
subMenu.addItem(menu);

menu = new Menu(path,"JavaModel");
//menu.addMenuItem("up", "../index.html",upicon);
menu.addMenuItem("JavaModel Loader", "index.html",menuicon);
menu.addMenuItem("JavaModel Architecture", "architecture.html",menuicon);
menu.addMenuItem("JDBC code generation", "jdbc_code.html",menuicon);
//menu.addMenuItem("JDBC code examples", "../../code/index.html",menuicon);
menu.addMenuItem("Xml code generation", "xml_code.html",menuicon);
menu.addMenuItem("SQL code generation", "sql_code.html",menuicon);
subMenu.addItem(menu);

menu = new Menu(path,"JavaModel Schema");
menu.addMenuItem("JavaModel Schema", "schema.html",menuicon);
menu.addMenuItem("JavaModel Schema Types", "schema_types.html",menuicon);
menu.addMenuItem("Example Blog Schema", "blog_schema.html",menuicon);
menu.addMenuItem("Example Student Schema", "student_schema.html",menuicon);
subMenu.addItem(menu);

/* bar and tab inherited from nav */
print(bar,xgenTab,subMenu);

