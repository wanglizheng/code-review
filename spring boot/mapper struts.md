````
@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article fromEntity(ArticleEntity entity);
    List<Article> fromEntityList(List<ArticleEntity> entityList);
}

````