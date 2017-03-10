Java's inability to instantiate objects of generic type causes a real problem with my intended method
bw end user would request graphicsobjects being created. Requiring user to instantiate the object directly
ie Graphics.loadShader(new MyShader()); rather than requesting an instantiation via Graphics.loadShader<MyShader>();
introduces a problem of ownership. Graphics.ShaderCollection is supposed to be canonical. 
Must find solution that maintains caonicality.