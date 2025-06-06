
### **1. Preparar el entorno local**
Antes de desplegar en Azure, asegúrate de que tu aplicación funcione correctamente en tu entorno local.

#### Pasos:
1. Verifica que todos los servicios definidos en 

docker-compose.yml

 funcionen correctamente:
   ```bash
   docker-compose up --build
   ```
2. Asegúrate de que los endpoints de tus microservicios (`inventario_microservice_app`, `pedidos_db`, etc.) estén accesibles y funcionando.

---

### **2. Crear un recurso de Azure para contenedores**
Azure ofrece varias opciones para ejecutar contenedores. La más común para aplicaciones basadas en Docker Compose es **Azure Container Apps** o **Azure Kubernetes Service (AKS)**.

#### Opción recomendada: **Azure Container Apps**
Azure Container Apps es más simple que AKS y soporta despliegues basados en Docker Compose.

---

### **3. Configurar Azure CLI**
Asegúrate de tener instalada la **Azure CLI** en tu máquina local. Si no la tienes, instálala desde [Azure CLI](https://learn.microsoft.com/en-us/cli/azure/install-azure-cli).

#### Pasos:
1. Inicia sesión en tu cuenta de Azure:
   ```bash
   az login
   ```
2. Selecciona la suscripción de Azure donde deseas desplegar:
   ```bash
   az account set --subscription "<ID-de-tu-suscripción>"
   ```

---

### **4. Crear un grupo de recursos**
Crea un grupo de recursos para organizar los recursos de Azure relacionados con tu aplicación.

```bash
az group create --name ProyectoPedidosRG --location eastus
```

---

### **5. Crear un registro de contenedores de Azure (ACR)**
El **Azure Container Registry (ACR)** se utiliza para almacenar las imágenes de Docker.

#### Pasos:
1. Crea un registro de contenedores:
   ```bash
   az acr create --resource-group ProyectoPedidosRG --name ProyectoPedidosACR --sku Basic
   ```
2. Inicia sesión en el registro de contenedores:
   ```bash
   az acr login --name ProyectoPedidosACR
   ```
3. Obtén la URL del registro:
   ```bash
   az acr show --name ProyectoPedidosACR --query "loginServer" --output tsv
   ```
   Esto devolverá algo como: `proyectopedidosacr.azurecr.io`.

---

### **6. Construir y subir las imágenes de Docker al ACR**
Modifica tu archivo 

docker-compose.yml

 para usar las imágenes del ACR.

#### Pasos:
1. Construye las imágenes de Docker:
   ```bash
   docker-compose build
   ```
2. Etiqueta las imágenes para el ACR:
   ```bash
   docker tag inventario_microservice_app proyectopedidosacr.azurecr.io/inventario_microservice_app
   docker tag pedidos_microservice_db proyectopedidosacr.azurecr.io/pedidos_microservice_db
   ```
3. Sube las imágenes al ACR:
   ```bash
   docker push proyectopedidosacr.azurecr.io/inventario_microservice_app
   docker push proyectopedidosacr.azurecr.io/pedidos_microservice_db
   ```

---

### **7. Desplegar en Azure Container Apps**
Usa Azure Container Apps para ejecutar los contenedores.

#### Pasos:
1. Crea un entorno de Azure Container Apps:
   ```bash
   az containerapp env create --name ProyectoPedidosEnv --resource-group ProyectoPedidosRG --location eastus
   ```
2. Despliega los contenedores:
   ```bash
   az containerapp create \
       --name InventarioApp \
       --resource-group ProyectoPedidosRG \
       --environment ProyectoPedidosEnv \
       --image proyectopedidosacr.azurecr.io/inventario_microservice_app \
       --cpu 0.5 --memory 1.0Gi \
       --ingress external --target-port 8081
   ```

   Repite este paso para otros servicios, como `pedidos_microservice_db`.

---

### **8. Configurar las variables de entorno**
Asegúrate de configurar las variables de entorno necesarias para cada contenedor. Puedes hacerlo al momento de crear el contenedor o actualizarlas después.

#### Ejemplo:
```bash
az containerapp update \
    --name InventarioApp \
    --resource-group ProyectoPedidosRG \
    --set-env-vars SPRING_DATASOURCE_URL=jdbc:mysql://inventario_db:3306/bd_inventario \
                   SPRING_DATASOURCE_USERNAME=usuario_api \
                   SPRING_DATASOURCE_PASSWORD=password_api
```

---

### **9. Configurar la red**
Si tus servicios necesitan comunicarse entre sí, asegúrate de que estén en la misma red virtual. Esto se puede configurar automáticamente en Azure Container Apps.

---

### **10. Verificar el despliegue**
1. Obtén la URL pública de los servicios desplegados:
   ```bash
   az containerapp show --name InventarioApp --resource-group ProyectoPedidosRG --query "properties.configuration.ingress.fqdn" --output tsv
   ```
2. Accede a los endpoints desde tu navegador o usa `curl` para probarlos:
   ```bash
   curl http://<fqdn>/api/inventario/status
   ```

---

### **11. Monitorear y escalar**
Azure Container Apps permite monitorear y escalar automáticamente los contenedores según la carga.

#### Escalado automático:
```bash
az containerapp revision set-mode --name InventarioApp --resource-group ProyectoPedidosRG --mode single
```