rootProject.name = "XLibrary"

include("core")
include("paper")
include("velocity")
include("bungee")

findProject(":core")?.name = "XLibrary-core"
findProject(":paper")?.name = "XLibrary-paper"
findProject(":velocity")?.name = "XLibrary-velocity"
findProject(":bungee")?.name = "XLibrary-bungee"
