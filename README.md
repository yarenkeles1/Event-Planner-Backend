# 🚀 JoinUp - Etkinlik Planlama Uygulaması

JoinUp, kullanıcıların etkinlik oluşturabildiği, etkinliklere katılım sağlayabildiği ve kendi etkinliklerini yönetebildiği bir web uygulamasıdır. Proje kapsamında backend ve frontend teknolojileri kullanılarak katmanlı mimariye uygun bir sistem geliştirilmiştir. Bu repository, JoinUp uygulamasının Spring Boot ile geliştirilmiş REST API servislerini içermektedir.

## Kullanılan Teknolojiler
* Java
* Spring Boot
* Spring Data JPA
* Validation 
* H2 Database
* Swagger
* DTO Yapısı
* Global Exception Handling  
* Http Session Authentication

 ## Proje Özellikleri

- Kullanıcı kayıt ve giriş işlemleri 
- Etkinlik oluşturma, güncelleme, silme ve arşivleme  
- Etkinlik listeleme ve arama  
- Sayfalama desteği ile etkinlik görüntüleme  
- Etkinlik katılım takibi  
- Kullanıcı bazlı etkinlik yönetimi  

## Kurulum 
### 1. Projeyi Klonlayın

```bash
git clone https://github.com/yarenkeles1/Event-Planner-Backend
cd event-planner-app 
   ```

### 2. Backend Servisini Başlatın

Projeyi bir IDE  ile açarak Spring Boot uygulamasını çalıştırın.

## 3. API Erişimi

Uygulama başarıyla başlatıldığında aşağıdaki servisler üzerinden erişim sağlanabilir:

- **Swagger UI:** `http://localhost:8081/swagger-ui.html`
- **H2 Database Console:** `http://localhost:8081/h2-console`
  
**Not:** H2 veritabanına giriş yaparken aşağıdaki bilgileri kullanabilirsiniz:
  
* **JDBC URL:** `jdbc:h2:file:~/event-planner-db`
* **Username:** `sa`
* **Password:** `sa`
