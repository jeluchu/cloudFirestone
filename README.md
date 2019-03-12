# CLOUD FIRESTORE
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![Download](https://img.shields.io/badge/Kotlin-1.3.21-brightgreen.svg?style=flat&logo=kotlin)](https://kotlinlang.org/docs/reference/whatsnew13.html)
[![Download](https://img.shields.io/badge/Gradle-4.10.1-brightgreen.svg?style=flat&logo=android)](https://services.gradle.org/distributions/gradle-4.10.1-all.zip)
[![API](https://img.shields.io/badge/J%C3%A9luchu-1.0.0-blue.svg?style=flat&logo=ello)](https://play.google.com/store/apps/dev?id=7449422814338081261&hl=es_ES)
---
## INTRODUCCIÓN
Se trata de un ejemplo de como usar nueva base de datos de "Firebase", llamada Cloud Firestore, con consultas más potentes que Realtime Database.

### INSTRUCCIONES

- Ve a [Firebase](https://console.firebase.google.com/)
- Crea un nuevo proyecto
- Introduce el siguiente nombre del paquete del proyecto:

````
com.jeluchu.cloudfirestone
````

- Añade el archivo "google-services.json" a la carpeta app
- Sincroniza el proyecto
- ¡Listo para compilar!

### DIFERENCIAS CON REALTIME DATABASE
Hemos podido comprobar que para la esta nueva base de datos tenemos la **colección** de datos, dentro de ella tenemos las diferentes **id's** de cada uno de los componentes que añadimos, y posteriormente los **campos** donde encontraremos los datos que hemos introducido desde la aplicación.

Por ejemplo, para añadir datos hemos hecho lo siguiente:
````
private fun addNote(title: String, content: String) {
    val note = Note(title, content).toMap()

    firestoreDB!!.collection("notes")
            .add(note)
            .addOnSuccessListener { documentReference ->
                Log.e(tag, "DocumentSnapshot written with ID: " + documentReference.id)
                Toast.makeText(applicationContext, "¡Datos añadidos!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(tag, "Error adding Note document", e)
                Toast.makeText(applicationContext, "Los datos no han sido añadidos", Toast.LENGTH_SHORT).show()
            }
}
````

Recordemos que para hacerlo en Realtime Database necesitábamos diversos métodos en los que defíniamos las acciones de la base de datos cuándo cambiaba, cuándo se eliminaba algún ítem, cuándo se modificaba o cuándo se añadían.
