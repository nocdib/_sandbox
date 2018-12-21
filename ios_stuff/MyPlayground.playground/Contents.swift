var optionalName: String? = nil//"John Appleseed"

var greeting = "Hello!"

if var name = optionalName {
   print("Hello, \(name)")
}

print("Hi \(optionalName ?? "fullName")")
