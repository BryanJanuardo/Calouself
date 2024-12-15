/**
 * 
 */
/**
 * 
 */
module OOAD_LAB_PROJECTAKHIR {
	requires java.sql;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.controls;
    opens Views.Customer to javafx.base;

    opens Main;
    opens Controllers;
    opens Models;
    opens Views;
    opens Factories;
    opens Helpers;
    opens Utils;
    opens Managers;
}