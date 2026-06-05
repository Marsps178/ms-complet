# 📊 GUÍA DE OBSERVABILIDAD - Madera Minería

Este documento explica cómo acceder y usar las herramientas de observabilidad del proyecto.

---

## 🎯 HERRAMIENTAS DISPONIBLES

Tu infraestructura tiene 3 pilares de observabilidad:

| Herramienta | Puerto | URL | Función |
|---|---|---|---|
| **Prometheus** | 9090 | http://localhost:9090 | Base de datos de métricas (scraping) |
| **Grafana** | 3000 | http://localhost:3000 | Dashboards visuales de métricas |
| **Kibana** | 5601 | http://localhost:5601 | Visualización de logs centralizados |
| **Elasticsearch** | 9200 | http://localhost:9200 | Motor de búsqueda para logs |
| **Logstash** | 5044 | - | Pipeline de procesamiento de logs |

---

## 1️⃣ PROMETHEUS - Métricas Brutas

### Acceso
```
http://localhost:9090
```

### ¿Qué hace?
- Recolecta métricas de tus microservicios cada 15 segundos
- Almacena series temporales de datos
- Es la base de datos para Grafana

### Cómo verificar que funciona

#### 📍 Ver Targets (microservicios monitoreados)
1. Ve a **Status** → **Targets**
2. Deberías ver verde (UP) tus servicios:
   - `ms-inventario:8082/actuator/prometheus`
   - `ms-pedidos:8083/actuator/prometheus`
   - `ms-auth:8085/actuator/prometheus`
   - Y otros...

#### 📊 Ver métricas disponibles
1. Ve a **Graph**
2. En el campo **Enter expression**, escribe una métrica:
   ```
   jvm_memory_used_bytes
   system_cpu_usage
   http_requests_total
   ```
3. Haz clic en **Execute**
4. Verás el gráfico en tiempo real

---

## 2️⃣ GRAFANA - Dashboards Visuales ⭐ RECOMENDADO

### Acceso
```
http://localhost:3000
```

### Login Inicial
- **Usuario:** `admin`
- **Contraseña:** `admin123`

### 📝 Primeros pasos

#### Paso 1: Conectar Prometheus como Data Source
1. Ve a **Configuration** → **Data Sources**
2. Haz clic en **Add data source**
3. Selecciona **Prometheus**
4. En **URL**, pon: `http://prometheus-madera:9090`
5. Haz clic en **Save & test**

#### Paso 2: Crear un Dashboard
1. Ve a **Dashboards** → **New** → **New Dashboard**
2. Haz clic en **Add Panel**
3. En el editor, escribe una query de Prometheus:

```
# CPU del sistema
rate(process_cpu_usage[5m])

# Memoria JVM usada
jvm_memory_used_bytes / 1024 / 1024

# Requests HTTP por segundo
rate(http_requests_total[1m])

# Errores HTTP
rate(http_requests_total{status=~"5.."}[1m])
```

#### Paso 3: Personalizar Panel
- Cambia el título (ej: "CPU Usage")
- Elige el tipo de visualización (gráfico, tabla, gauge, etc.)
- Añade más métricas según necesites

### 📊 Dashboards de ejemplo que puedes crear

#### Dashboard 1: Health Check
```
Título: Salud de Microservicios
Métricas:
- UP/DOWN status de cada servicio
- CPU por servicio
- Memoria por servicio
```

#### Dashboard 2: Tráfico HTTP
```
Título: Tráfico de API Gateway
Métricas:
- Requests por segundo
- Latencia promedio
- Errores (5xx)
- Códigos de respuesta
```

#### Dashboard 3: Base de Datos
```
Título: Performance PostgreSQL
Métricas:
- Conexiones activas
- Queries lentas
- Pool de conexiones
```

---

## 3️⃣ KIBANA - Logs Centralizados

### Acceso
```
http://localhost:5601
```

### 📋 Configurar Index Pattern

#### Paso 1: Crear Index Pattern
1. Ve a **Stack Management** → **Index Patterns** (en el menú izquierdo)
2. Haz clic en **Create index pattern**
3. Verás tus índices disponibles:
   - `madera-mineria-logs-2026.05.22`
   - `madera-mineria-logs-2026.06.05`
4. En **Index pattern name**, pon: `madera-mineria-logs-*`
5. En **Timestamp field**, selecciona: `@timestamp`
6. Haz clic en **Create index pattern**

#### Paso 2: Ver Logs en Discover
1. Ve a **Discover** (menú izquierdo)
2. Selecciona el index pattern: `madera-mineria-logs-*`
3. Verás todos tus logs con:
   - Timestamp
   - Nivel de log (INFO, WARN, ERROR)
   - Mensaje
   - Servicio que lo generó

### 🔍 Cómo buscar y filtrar

#### Buscar por nivel
```
level: ERROR
```

#### Buscar por microservicio
```
service: "ms-pedidos"
```

#### Buscar por palabra clave
```
message: "NullPointerException"
```

#### Combinar filtros
```
level: ERROR AND service: "ms-inventario"
```

### 📈 Crear Dashboards de Logs
1. Ve a **Dashboards** → **Create dashboard**
2. Añade visualizaciones de logs
3. Ejemplos:
   - Errores por hora
   - Errores por servicio
   - Top 10 excepciones

---

## 🔄 CÓMO FLUYEN LOS LOGS

```
Microservicios (Logback)
    ↓
Logstash (puerto 5044)
    ↓
Elasticsearch (puerto 9200)
    ↓
Kibana (puerto 5601)
```

---

## ✅ CHECKLIST DE VERIFICACIÓN

Ejecuta esto en tu terminal para verificar que todo funciona:

```bash
# Verificar Prometheus
curl http://localhost:9090/api/v1/targets

# Verificar Elasticsearch
curl http://localhost:9200

# Verificar Logstash
telnet localhost 5044  # Si responde, está activo

# Verificar logs en Elasticsearch
curl "http://localhost:9200/madera-mineria-logs-*/_search" | grep -o '"_index":"[^"]*"' | head -5
```

---

## 🚨 TROUBLESHOOTING

### Problema: Prometheus no ve mis microservicios
**Solución:** Verifica que los servicios tengan `/actuator/prometheus` habilitado en `application.properties`

```properties
management.endpoints.web.exposure.include=health,prometheus,metrics
management.metrics.export.prometheus.enabled=true
```

### Problema: No hay logs en Kibana
**Solución:** 
1. Verifica que Logstash esté corriendo: `docker-compose logs logstash`
2. Verifica que los logs lleguen a Elasticsearch: `curl http://localhost:9200/_cat/indices`

### Problema: Grafana no conecta con Prometheus
**Solución:**
1. Usa URL interna del Docker: `http://prometheus-madera:9090`
2. NO uses `http://localhost:9090` desde dentro de Grafana

---

## 📚 QUERIES ÚTILES DE PROMETHEUS

```
# JVM Memory
jvm_memory_used_bytes{job="prometheus"} / 1024 / 1024

# HTTP Requests Rate
rate(http_requests_total[5m])

# Error Rate
rate(http_requests_total{status=~"5.."}[5m])

# CPU Usage
process_cpu_usage

# Database Connections
db_connections_active

# API Latency (P95)
histogram_quantile(0.95, rate(http_request_duration_seconds_bucket[5m]))
```

---

## 🎓 BUENAS PRÁCTICAS

1. **Crea dashboards por rol:**
   - DevOps: salud de infraestructura
   - Developers: errores y latencia
   - PM: usuarios activos, transacciones

2. **Configura alertas en Grafana:**
   - CPU > 80%
   - Errores 5xx > 0.1%
   - Latencia > 1s

3. **Retén logs importantes:**
   - Conserva 7 días de logs en hot storage
   - Archive 30 días en cold storage

4. **Monitorea estos KPIs:**
   - Error rate
   - Latency (p50, p95, p99)
   - Throughput
   - Saturation

---

## 📞 AYUDA

Si necesitas:
- **Nuevas métricas:** Actualiza `application.properties` en tus microservicios
- **Filtros avanzados:** Aprende LanguageQuery en Kibana
- **Alertas:** Configúralas en Grafana
- **Performance:** Ajusta retention en Elasticsearch

---

**Última actualización:** 5 de junio de 2026
