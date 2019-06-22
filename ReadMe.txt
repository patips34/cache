
In realworld application, we can use caching mechanism supported by advanced features, annotations provided by spring/spring boot/JavaEE frameworks.
Some of the caches are Redis, memcache, infinispan etc. Here caches are implemented using map and mongodb.

- L1 cache
	- implemented as "InternalCache" in enclosing project.
	- Used ConcerrentHashMap as databastructure.
	- Used Size and TTL configurations.
	- used TTL as cache eviction policy.
	
- L2 Cache
	- implemented as "ExternalCache" in enclosing project.
	- Used MongoDB as secondary storage to implement this usecase.
	- Used TTL as cache eviction policy.
	- no size limitation added for l2 cache.
	