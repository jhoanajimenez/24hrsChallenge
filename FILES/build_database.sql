/**must be executed in this order**/


/**for orders tbl**/
CREATE TABLE `test`.`orders`( 
   `order_id` INT(10) NOT NULL AUTO_INCREMENT , 
   `date` DATE NOT NULL , 
   `time` TIME NOT NULL , 
   PRIMARY KEY (`order_id`)
 );

/**for pizza_types tbl**/
 CREATE TABLE `test`.`pizza_types`( 
   `pizza_type` VARCHAR(50) NOT NULL , 
   `name` VARCHAR(120) NOT NULL , 
   `category` ENUM('Chicken','Classic','Supreme','Veggie') NOT NULL , 
   `ingredients` LONGTEXT NOT NULL,
   PRIMARY KEY (`pizza_type`)
 );
 
 /**for pizzas tbl**/
 CREATE TABLE `test`.`pizzas`( 
   `pizza_id` VARCHAR(50) NOT NULL , 
   `pizza_type` VARCHAR(50) NOT NULL , 
   `size` ENUM('S','M','L','XL','XXL') NOT NULL , 
   `price` DECIMAL(20,2) NOT NULL , 
   PRIMARY KEY (`pizza_id`),
   FOREIGN KEY (pizza_type) REFERENCES pizza_types(pizza_type)
 );

 /**for order_details tbl**/
 CREATE TABLE `test`.`order_details`( 
   `order_details_id` INT(10) NOT NULL AUTO_INCREMENT , 
   `order_id` INT(10) NOT NULL , 
   `pizza_id` VARCHAR(50) NOT NULL , 
   `quantity` INT(10) NOT NULL , 
   PRIMARY KEY (`order_details_id`),
   FOREIGN KEY (order_id) REFERENCES orders(order_id),
   FOREIGN KEY (pizza_id) REFERENCES pizzas(pizza_id)
 );
 
/*import the csv files*/
LOAD DATA LOCAL INFILE orders.csv IGNORE INTO TABLE `pizza_orders`.orders FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n'  IGNORE 1 LINES;
LOAD DATA LOCAL INFILE pizza_types.csv IGNORE INTO TABLE `pizza_orders`.pizza_types FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n'  IGNORE 1 LINES;
LOAD DATA LOCAL INFILE pizzas.csv IGNORE INTO TABLE `pizza_orders`.pizzas FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n'  IGNORE 1 LINES;
LOAD DATA LOCAL INFILE order_details.csv IGNORE INTO TABLE `pizza_orders`.order_details FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\r\n'  IGNORE 1 LINES;
