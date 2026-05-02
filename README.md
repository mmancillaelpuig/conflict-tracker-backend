# Conflict Tracker

## URL pública del frontend

conflict-tracker-frontend-jj25l19zn.vercel.app

## Arquitectura utilitzada

```txt
Usuari
  ↓
Frontend Vue 3 + Vite desplegat a Vercel
  ↓
Peticions HTTP amb fetch
  ↓
Backend Spring Boot desplegat a Render/Railway
  ↓
JPA / Hibernate
  ↓
Base de dades PostgreSQL



Variables d'entorn per a un nou desplegament

Frontend - Vercel:

VITE_API_URL=https://TU-BACKEND.onrender.com/api/v1

Backend - Render/Railway:

DB_URL=jdbc:postgresql://HOST:5432/NOM_BASE_DE_DADES
DB_USER=USUARI
DB_PASSWORD=CONTRASENYA
PORT=8080
Modificacions realitzades
Backend Spring Boot

Al principi el backend no arrencava correctament en producció. Un dels problemes era que el fitxer Docker estava anomenat com DockerFile, i la plataforma de desplegament no el detectava correctament. S'ha solucionat canviant el nom del fitxer a Dockerfile.

També hi havia un problema amb la versió de Java. El projecte estava configurat per utilitzar Java 21, però el Dockerfile feia servir Java 17. S'ha solucionat actualitzant el Dockerfile a Java 21.


També s'ha afegit la configuració del port dinàmic, necessària perquè Render/Railway assigni el port correcte en producció.
    server.port=${PORT:8080}

La connexió a PostgreSQL s'ha configurat mitjançant variables d'entorn per no deixar dades sensibles escrites directament al codi.
    spring.datasource.url=${DB_URL}spring.datasource.username=${DB_USER}spring.datasource.password=${DB_PASSWORD}

Frontend Vue
    Al principi el deploy del frontend a Vercel fallava amb aquest error:
        vite: command not found

El problema era que Vercel estava executant directament vite build. S'ha solucionat configurant el build command com:
    npm run build

La configuració final del projecte a Vercel és:
    Framework Preset: ViteRoot Directory: conflict-trackerInstall Command: npm installBuild Command: npm run buildOutput Directory: dist

També s'ha configurat la URL del backend amb la variable d'entorn:
    VITE_API_URL=https://TU-BACKEND.onrender.com/api/v1

Per evitar errors 404 en rutes internes de Vue Router, s'ha afegit el fitxer vercel.json.
{  "rewrites": [    {      "source": "/:path*",      "destination": "/index.html"    }  ]}


Execució local

Backend:
    mvn spring-boot:run

Frontend:
    cd conflict-trackernpm installnpm run dev
