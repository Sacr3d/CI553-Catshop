# Author: Mattia Sassone
# Version: 1.0

AUTHOR:= Mattia Sassone
VERSION:= 1.0

TARGET:= target

DERBY_DATABASE:= catshop.db

DERBY_DATABASE_TXT:= echo "Derby" > DataBase.txt

TARGET_JAR:= $(shell cd target && find . -maxdepth 1 -name 'original-*' | cut -d'-' -f2-)
TARGET_JAR_CATSHOP:= $(shell find $(TARGET)/ -maxdepth 1 -name '*-catshop.jar')
TARGET_JAR_SETUP:= $(shell find $(TARGET)/ -maxdepth 1 -name '*-setup.jar')

CATSHOP_JAR:= catshop.jar
SETUP_JAR:= setup.jar

JAVA_PACKAGE:= com.example.app
JAVA_PACKAGE_CLIENTS:= $(JAVA_PACKAGE).clients
JAVA_PACKAGE_MIDDLE:= $(JAVA_PACKAGE).middle

JAVA_PACKAGE_PATH:= com/example/app

MAIN_CLASS:= $(JAVA_PACKAGE).Main
SETUP_CLASS:= $(JAVA_PACKAGE_CLIENTS).Setup
SERVER_CLASS:= $(JAVA_PACKAGE_MIDDLE).Server

BACKDOOR_CLASS:= $(JAVA_PACKAGE_CLIENTS).backDoor.BackDoorClient
CUSTOMER_CLASS:= $(JAVA_PACKAGE_CLIENTS).customer.CustomerClient
CAHSIER_CLASS:= $(JAVA_PACKAGE_CLIENTS).cashier.CashierClient
PICK_CLASS:= $(JAVA_PACKAGE_CLIENTS).warehousePick.PickClient
DISPLAY_CLASS:= $(JAVA_PACKAGE_CLIENTS).shopDisplay.DisplayClient
COLLECT_CLASS:= $(JAVA_PACKAGE_CLIENTS).collection.CollectClient

install:
	@echo "Cleaning previous package"
	@$(MAKE) clean-all
	@echo "Derby Preperation"
	@$(DERBY_DATABASE_TXT)
	@echo "Installing with Maven"
	@mvn install
	@echo "Copying artifacts"
	@cd $(TARGET);				\
		$(DERBY_DATABASE_TXT);	\
		cd ..
	cp -r images $(TARGET)
	@$(MAKE) clean-local
	@$(MAKE) post-install

post-install:
	@echo "Prepareing Derby install"
	@$(DERBY_DATABASE_TXT)
	@echo "Renaming Snapshots"
	@mv $(TARGET_JAR_CATSHOP) $(CATSHOP_JAR)
	@mv $(TARGET_JAR_SETUP) $(SETUP_JAR)

package:
	@echo "Cleaning previous package"
	@$(MAKE) clean-all
	@echo "Derby Preperation"
	@$(DERBY_DATABASE_TXT)
	@echo "Packaging with Maven"
	@mvn package
	@echo "Copying artifacts"
	@cd $(TARGET);							\
		$(DERBY_DATABASE_TXT);				\
		cd ..
	@cp -r images $(TARGET)
	@$(MAKE) clean-local

compile:
	@echo "Compiling with Maven"
	@mvn compile

clean-all:
	@echo "Cleaning Maven"
	@mvn clean
	@$(MAKE) clean-lib
	@$(MAKE) clean-local

clean-local:
	@echo "Cleaning local"
	rm -f -r catshop.db
	rm -f -r DataBase.txt
	rm -f -r derby.log
	rm -f -r $(CATSHOP_JAR)
	rm -f -r $(SETUP_JAR)

clean-lib:
	@echo "Cleaning libaries"
	rm -f -r lib

doc:
	@echo "Making documentation with Maven"
	@mvn javadoc:jar

jarrun:
ifneq (,$(wildcard DataBase.txt))
		java -jar $(SETUP_JAR);			\
		java -jar $(CATSHOP_JAR)
else
	@echo "No local installation found please run 'make install'"
endif

test:
	@$(MAKE) clean-local;
	@$(DERBY_DATABASE_TXT);
	@mvn test
	@$(MAKE) clean-local

distributed: 
	@$(MAKE) database
	@$(MAKE) server
	@$(MAKE) clients
	@read -p "CTRL + C to exit or Enter to kill all Java Processes"
	@$(MAKE) kill-all

server:
	@echo "Making $@"
ifneq (,$(wildcard $(TARGET)$(DERBY_DATABASE)))
	@cd $(TARGET);									\
		java -cp ./$(TARGET_JAR) $(SERVER_CLASS)&
	@sleep 2
	@read -p "CTRL + C to exit or Enter to continue distribution"
else
	@echo "No database please run 'make database'"
endif

clients:
	@echo "Making $@"
ifneq (,$(wildcard $(TARGET)$(DERBY_DATABASE)))
	@cd $(TARGET);									\
		echo **Backdoor&							\
		java -cp $(TARGET_JAR) $(BACKDOOR_CLASS)&	\
		echo **Customer&							\
		java -cp $(TARGET_JAR) $(CUSTOMER_CLASS)&	\
		echo **Cashier&								\
		java -cp $(TARGET_JAR) $(CAHSIER_CLASS)&	\
		echo **Pick&								\
		java -cp $(TARGET_JAR) $(PICK_CLASS)&		\
		echo **BackDisplaydoor&						\
		java -cp $(TARGET_JAR) $(DISPLAY_CLASS)&	\
		echo **Collect&								\
		java -cp $(TARGET_JAR) $(COLLECT_CLASS)&
	@sleep 1
	@read -p "CTRL + C to exit or Enter to continue distribution"
else
	@echo "No database please run 'make database'"
endif

database:
	@echo "Making $@"
	@cd $(TARGET);		\
		java -cp $(TARGET_JAR) $(SETUP_CLASS)

kill-all:
	@echo "Killing all Java Processes"
	@kill $(shell ps aux | grep -i 'java' | grep -v 'grep' | awk '{print $2}')
