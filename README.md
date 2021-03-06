DataBindingAdapter [![Build Status](https://travis-ci.org/williamwebb/DataBindingAdapter.svg?branch=master)](https://travis-ci.org/williamwebb/DataBindingAdapter) [![Join the chat at https://gitter.im/williamwebb/chat](https://badges.gitter.im/williamwebb/chat.svg)](https://gitter.im/williamwebb/chat?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
============

Android RecyclerView Adapter powered by DataBinding.

Usage
=====
100% configurable via XML

```xml
<android.support.v7.widget.RecyclerView
	...
	app:layoutManager="@{LayoutManagers.linear()}"
	app:binding_data="@{dataToBind}"
	app:binding_layout="@{@layout/layoutName}"
	app:binding_variable='@{"bindingVariableName"}'
/>
```
Thats it!


Download
--------

Download the latest JAR via Maven:


```xml
<dependency>
  <groupId>io.jug6ernaut</groupId>
  <artifactId>databindingadapter</artifactId>
  <version>1.0.1</version>
</dependency>
```

or Gradle:


```groovy
compile 'io.jug6ernaut:databindingadapter:1.0.1'
```


License
-------

    Copyright 2016 William Webb

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
