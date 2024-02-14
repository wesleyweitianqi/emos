### Project 

#### dependencies
- springBoot DevTool
- Lombok
- Spring Web
- Mysql Driver
- Mybatis Framework
- Spring Data Redis
- Spring data MongoDB
- Spring For RabbitMQ
- Java Mail Sender
- QuartZ Scheduler

#### Redis Config
Add new line below `requirepass foobare`  to add authentication

#### MongoDB config
Enter mongosh and create user and roles 
```
db.createUser(
 {
   user: "reportsUser",
   pwd: passwordPrompt(),  // or cleartext password if you wish
   roles: [
      { role: "read", db: "reporting" },
      { role: "read", db: "products" },
      { role: "read", db: "sales" },
      { role: "readWrite", db: "accounts" }
   ]
 }
)
```
