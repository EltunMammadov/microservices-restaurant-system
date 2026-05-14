Restaurant Microservice Sistemi

Bu layihə **Spring Boot, Kafka və Docker** istifadə edilərək qurulmuş **mikroservis arxitekturalı restoran idarəetmə sistemidir**.

Sistem **event-driven architecture** əsasında işləyir və servisler arası kommunikasiya Kafka vasitəsilə həyata keçirilir.

---

Texnologiyalar

- Java 17+
- Spring Boot
- Spring Cloud (Microservices)
- Apache Kafka
- Docker & Docker Compose
- PostgreSQL / MySQL
- Elasticsearch & Kibana
- REST API
- Maven

---

🏗️ Sistem Arxitekturası

Layihə aşağıdakı mikroservislərdən ibarətdir:

-  **Restaurant Service** → restoran məlumatlarının idarə edilməsi
-  **Order Service** → sifarişlərin yaradılması və idarəsi
-  **Notification Service** → istifadəçilərə bildirişlərin göndərilməsi
-  **Delivery Service** → sifarişlərin çatdırılma prosesinin idarəsi
-  **Menu Service** → menyu və məhsulların idarə edilməsi
-  **Kafka Broker** → servisler arası event kommunikasiya
-  **ELK Stack (Elasticsearch + Kibana)** → logların izlənməsi və analiz

---

## Mikroservislərin Funksiyaları

###  Restaurant Service
- Restoran CRUD əməliyyatları
- Restoran məlumatlarının idarəsi

---

###  Menu Service
- Menyu yaradılması və yenilənməsi
- Məhsulların kateqoriyalara bölünməsi
- Restoran ilə əlaqəli menu idarəsi

---

###  Order Service
- Sifariş yaradılması
- Sifariş statusunun idarəsi
- Kafka event publish

---

###  Notification Service
- Order event-lərini dinləyir
- Email / notification göndərir
- Async işləyir (Kafka consumer)

---

###  Delivery Service
- Sifarişlərin çatdırılma statusu
- Courier tracking logic
- Delivery lifecycle management

---

##  Event Flow (Kafka)

Sistem tam event-driven işləyir:
