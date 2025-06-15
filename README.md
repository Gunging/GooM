## Gunging Ootilities Mod

`build.gradle`
```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation fg.deobf("io.github.gunging:gungingoom:0.0.3-1.20.1")
}
```

"A lie, or perhaps, a happy ending" or something like that

Well okay, it is just an utilities library plugin. Things focused on
inventory management which is my specialty. At the very least these
carry my promise, that they will never break. My design prioritizes
lowest maintenance and highest backward compatibility.

--------------------------------------------------------------------

For context, I once developed a plugin, GooP, with the idea to one
day develop a mod counterpart and have them connect. However,
I kind of made it when I was learning Java and Object-Oriented
Programming so GooP it is a mix of madness and trial and error.

GooP gets the job done most of the time and I am proud of its
backward compatibility supporting mc 1.13 through 1.21.

Anyway, I will first recode GooP than make it connect to GooM.
As of now, GooM is just a parallel utilities library lol.
