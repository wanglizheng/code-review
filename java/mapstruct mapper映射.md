# Pom依赖

````
    <properties>
        <mapstruct.version>1.3.0.Final</mapstruct.version>
        <swagger.version>2.9.2</swagger.version>
    </properties>
    
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
````

# 创建接口类

````
@Mapper(componentModel = "spring")
public interface RelationXiaoDuMapper {

    List<RelationshipXiaoDuShow> fromRelationships(List<RelationShip> relationShips);
}


````