package senacor.cub.samples.service.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import senacor.cub.samples.service.article.command.ArticleRepository;
import senacor.cub.samples.service.article.command.articledetail.Article;
import senacor.cub.samples.service.article.command.articledetail.ArticleDetailCommand;
import senacor.cub.samples.service.article.command.articlelist.ArticleListCommand;
import senacor.cub.samples.service.article.command.articlelist.Articles;
import senacor.cub.samples.technical.ping.Pong;

@CrossOrigin(origins = "http://192.168.99.100:10080")
@RestController
@RequestMapping(value = "/articlesrv/api/v1")
public class ArticleController {

    @Autowired
    private ArticleListCommand articleListCommand;

    @Autowired
    private ArticleDetailCommand articleDetailCommand;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Pong ping() {
        return new Pong();
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> getArticle(@PathVariable("articleId") String articleId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setCacheControl("max-age=30");

        return new ResponseEntity<Article>(articleDetailCommand.getArticle(articleId), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Articles getArticles(@RequestParam(value = "pn", defaultValue = "0") int pagenum, @RequestParam(value = "ps", defaultValue = "5") int pagesize) {
        return articleListCommand.getArticles(pagenum, pagesize);
    }

    // ---- just for demo, please ignore

    @Autowired
    private ArticleRepository repository;

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public String populate() {
        repository.deleteAll();

        repository.save(new Article("100", "TV", "Panasonic", "Viera 32", 500, 10));
        repository.save(new Article("101", "TV", "Panasonic", "Viera 40", 900, 20));
        repository.save(new Article("102", "TV", "Panasonic", "Viera 46", 1200, 20));
        repository.save(new Article("103", "TV", "Panasonic", "Viera 50", 1500, 20));
        repository.save(new Article("104", "TV", "Panasonic", "Viera 60", 1999, 40));
        repository.save(new Article("105", "TV", "Sony", "Bravia 37", 500, 10));
        repository.save(new Article("106", "TV", "Sony", "Bravia 42", 990, 20));
        repository.save(new Article("107", "TV", "Sony", "Bravia 48", 1250, 20));
        repository.save(new Article("108", "TV", "Sony", "Bravia 52", 1570, 30));
        repository.save(new Article("109", "TV", "Sony", "Bravia 66", 2800, 40));
        repository.save(new Article("200", "Phone", "iPhone", "4", 220, 10));
        repository.save(new Article("201", "Phone", "iPhone", "4s", 260, 10));
        repository.save(new Article("202", "Phone", "iPhone", "5", 280, 10));
        repository.save(new Article("203", "Phone", "iPhone", "5s", 310, 10));
        repository.save(new Article("204", "Phone", "iPhone", "5c", 290, 10));
        repository.save(new Article("205", "Phone", "iPhone", "6", 400, 10));
        repository.save(new Article("206", "Phone", "iPhone", "6+", 450, 10));
        repository.save(new Article("207", "Phone", "iPhone", "6s", 700, 10));
        repository.save(new Article("208", "Phone", "iPhone", "6s+", 900, 10));
        repository.save(new Article("300", "T-Shirt", "Superdry", "casual", 70, 10));
        repository.save(new Article("301", "T-Shirt", "Hollister", "casual", 40, 10));
        repository.save(new Article("302", "T-Shirt", "A&F", "casual", 50, 10));
        repository.save(new Article("303", "T-Shirt", "Reece", "sport", 35, 10));
        repository.save(new Article("304", "T-Shirt", "Puma", "sport", 45, 10));
        repository.save(new Article("305", "T-Shirt", "Adidas", "sport", 47, 10));

        return repository.findAll().size()+" articles created";
    }
}