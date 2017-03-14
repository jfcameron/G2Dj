Java generics done via erasure. cannot do such things as T t new T() or something instanceof T. etc.
GameObject.addComponent(T) works however
GameObject.removeComponent(T) does not supply enough info to find T in the component vector
1 solution: hashmap <String,Component> and removeComponent(final String aComponentType).
Not elegant but allows me to continue to not allow user to instantiate class types owned by the engine outside of the engine.