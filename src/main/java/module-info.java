module lk.ijse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.jfoenix;
    //requires java.mail;
//    requires net.sf.jasperreports.core;
//    requires javax.mail.api;
//    requires xmlgraphics.commons;
    requires lombok;
    requires jakarta.persistence;
    requires jbcrypt;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.mail;



    opens lk.ijse.controller to javafx.fxml;
    opens lk.ijse.entity to org.hibernate.orm.core;
    opens lk.ijse.config to jakarta.persistence;
    opens lk.ijse.view.tdm to javafx.base;

//    opens edu.ijse.bookshop.view.tdm to javafx.base;
    exports lk.ijse;
}