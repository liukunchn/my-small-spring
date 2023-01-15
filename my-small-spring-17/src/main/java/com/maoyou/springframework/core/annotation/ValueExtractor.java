/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.com.maoyou/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maoyou.springframework.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import com.maoyou.springframework.lang.Nullable;

/**
 * Strategy API for extracting a value for an annotation attribute from a given
 * source object which is typically an {@link Annotation}, {@link Map}, or
 * {@link TypeMappedAnnotation}.
 *
 * @since 5.2.4
 * @author Sam Brannen
 */
@FunctionalInterface
interface ValueExtractor {

	/**
	 * Extract the annotation attribute represented by the supplied {@link Method}
	 * from the supplied source {@link Object}.
	 */
	@Nullable
	Object extract(Method attribute, @Nullable Object object);

}
