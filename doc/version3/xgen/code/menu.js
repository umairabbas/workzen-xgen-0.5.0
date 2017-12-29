/* 
 * code menu.js 
 * Brad Matlack 4-2004
 */

var project = "XGen";
var path = basepath + "/xgen/code";

//var subMenu = new Container();

var menu = new Menu(path,"JavaModel");
menu.addMenuItem("up", "../loaders/java/index.html",upicon);
subMenu.addItem(menu);

menu = new Menu(path,"POJO","address",menuicon);
menu.addMenuItem("Blog.java", "Blog.java.html",menuicon);
menu.addMenuItem("BlogCategory.java", "BlogCategory.java.html",menuicon);
subMenu.addItem(menu);


var menu = new Menu(path,"JDBC","address");
//menu.addMenuItem("Persistable class");
//menu.addMenuItem("Blog.java", "Blog.java.html",menuicon);
//menu.addMenuItem("BlogCategory.java", "BlogCategory.java.html",menuicon);

menu.addMenuItem("Persistence tables");
menu.addMenuItem("BlogTable.java", "BlogTable.java.html",menuicon);
menu.addMenuItem("BlogCategoryTable.java", "BlogCategoryTable.java.html",menuicon);

menu.addMenuItem("Persistence managers");
menu.addMenuItem("BlogManager.java", "BlogManager.java.html",menuicon);
menu.addMenuItem("BlogCategoryManager.java", "BlogCategoryManager.java.html",menuicon);
subMenu.addItem(menu);

/* bar and tab inherited from nav */
print(bar,xgenTab,subMenu);

