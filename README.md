# Weather Station

### Envinronment variables:
1. OPEN_WEATHER_MAP_APPID
2. PORT

### Technical specification
1. Spring Boot jar app
2. Use of RestTemplate
3. Scheduled function requesting for weather data
4. Use of Actuator
5. Use of Metrix
6. Port for Prometheus

### Technical backlog:
1. Run as Docker Image
2. Upload to AWS Linux
3. Introduce OpenWeatherAPI key as env
4. Applicaction:
   * add other charts: meter, gauge,
5. Prometheus:
   * secure connection
   * change port from default one
   * run as Docker - docker-compose
   * add alert levels and alert channels
   * keep data only one week past
   * what kind of db is there ?
6. Grafana:
   * chart of wind direction
   * chart of wind speed
   * chart of wind gust
   * diagnostic data charts

### Functional backlog:
1. add other weather data source:
   * tommorow.io ?
   * stormglass ?
   * yahoo weather ? 
   * Accu Weather ?
   * Meteomatics ?
2. displaying diffs between graphs from different sources (Grafana)
3. add web UI - chart displaying wind directions

### Cheatsheet:
1. docker container run -p 9090:9090 -v {folderPath}/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus:v2.35.0
2. http://dev.local:8080/actuator/prometheus
3. http://dev.local:9090/graph

### Resources
1. ! https://www.tutorialworks.com/spring-boot-prometheus-micrometer/
2. https://prometheus.io/
3. https://www.tutorialworks.com/docker-java-best-practices/
4. https://stackabuse.com/monitoring-spring-boot-apps-with-micrometer-prometheus-and-grafana/
5. https://tanzu.vmware.com/developer/guides/spring-prometheus/
6. https://hub.docker.com/r/prom/prometheus
7. https://docs.spring.io/spring-metrics/docs/current/public/prometheus
8. https://raymondhlee.wordpress.com/2016/10/03/monitoring-spring-boot-applications-with-prometheus-part-2/
9. https://developpaper.com/prometheus-integration-spring-boot-2/
10. https://www.baeldung.com/spring-boot-actuators

