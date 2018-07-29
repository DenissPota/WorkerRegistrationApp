# WorkerRegistrationApp
Project to register workers and their field of work in single application.

**Used technologies**

1. Spring Boot as backend
3. JPA and Hibernate as ORM
2. [Vue.js](https://vuejs.org/) as frontend JavaScript Framework
4. [Vuetify](https://vuetifyjs.com/en/) as Material Design Component Framework
5. Docker for containerization

Database dump can be found in on the same level as README.md, and is named worker_reg_dbdump
  
---

**Architecture**
  
Application uses 2 servers of which, first one has Docker container with JAR file that has all the
files from backend/frontend under port 8080 mapped to port 80.

Second server is used purely as Postgresql DB holder. 

---
  
**Known bugs**

1. In field selector, changing industry selector after changing subsector/speciality triggers server-side error.
2. Editing worker does not show his previous working field tree.

---

**App 2min preview**

<a href="http://www.youtube.com/watch?feature=player_embedded&v=iK37azkOm0Y
" target="_blank"><img src="http://img.youtube.com/vi/iK37azkOm0Y/0.jpg" 
alt="preview" width="240" height="180" border="10" /></a>