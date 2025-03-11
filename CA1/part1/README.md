:doctype: book
:tags: [rest,hateoas,data,react,security]
:projects: [spring-data-rest,spring-data-jpa,spring-hateoas,spring-security,spring-boot,]
:toc: left
:icons: font
:source-highlighter: prettify
:image-width: 500

# This repository is no longer maintained.

= React.js and Spring Data REST

This tutorial shows a collection of apps that use Spring Data REST and its powerful backend functionality, combined with React's sophisticated features to build an easy-to-understand UI.

* https://www.youtube.com/watch?v=TgCr7v9tdKM[Spring Data REST] provides a fast way to build hypermedia-powered repositories.
* https://facebook.github.io/react/index.html[React] is Facebook's solution to efficient, fast, and easy-to-use views in JavaScript.

include::basic/README.adoc[leveloffset=+1]
include::hypermedia/README.adoc[leveloffset=+1]
include::conditional/README.adoc[leveloffset=+1]
include::events/README.adoc[leveloffset=+1]
include::security/README.adoc[leveloffset=+1]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/footer.adoc[]

## CA1
### Part 1

Created the DevOps folder (devops-24-25-1241906) through the git bash terminal:
devops-24-25-1241906v
cd devops-24-25-1241906v
git init  -- local repository
mkdir CA1 CA1/part1

Imported the basic folder aswell as the pom.xml from the Tutorial React.js and Spring Data REST to the
part1 folder.

Created a tag for the version of the application:
git tag -a v1.1.0 -m "Initial version 1.1.0"
Pushed the updated tag:
git push origin v1.1.0

Changed private Employee to protected Employee in the Employee Class.

Employee class: Created attribute jobYears - added it to the constructor, updated overrides and added
getter and setter.
Updated getters and setters to throw IllegalArgumentExceptions.
Updated its constructor to include the setters validations.

Added jobYears to CA1/part1/basic/src/main/js/app.js.
Added the jobYears value in the DatabaseLoader class.

Created the directory CA1/part1/basic/src/tests for the tests.
Generated EmployeeTest Class, and created the methods' tests.
Updated app.js to include jobYears.

Git bash terminal:
cd devops-24-25-1241906\CA1\part1\basic
./mvnw spring-boot:run


