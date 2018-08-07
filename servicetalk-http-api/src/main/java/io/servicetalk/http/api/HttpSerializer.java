/*
 * Copyright © 2018 Apple Inc. and the ServiceTalk project authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.servicetalk.http.api;

import io.servicetalk.buffer.api.Buffer;
import io.servicetalk.buffer.api.BufferAllocator;
import io.servicetalk.serialization.api.SerializationException;
import io.servicetalk.serialization.api.TypeHolder;

import java.lang.reflect.ParameterizedType;
import java.util.function.IntUnaryOperator;

/**
 * A factory to address serialization/deserialization concerns for HTTP request/response payload bodies.
 */
public interface HttpSerializer {

    /**
     * Transforms the passed {@link AggregatedHttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a single {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param <T> The data type to serialize.
     *
     * @return An {@link AggregatedHttpRequest} which represents the serialized form of {@code request}.
     */
    <T> AggregatedHttpRequest<HttpPayloadChunk> serialize(AggregatedHttpRequest<T> request, BufferAllocator allocator);

    /**
     * Transforms the passed {@link AggregatedHttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a single {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param bytesEstimate An estimate as to how many bytes each serialization will require.
     * @param <T> The data type to serialize.
     *
     * @return An {@link AggregatedHttpRequest} which represents the serialized form of {@code request}.
     */
    <T> AggregatedHttpRequest<HttpPayloadChunk> serialize(AggregatedHttpRequest<T> request, BufferAllocator allocator,
                                                          int bytesEstimate);

    /**
     * Transforms the passed {@link AggregatedHttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a single {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param <T> The data type to serialize.
     *
     * @return An {@link AggregatedHttpResponse} which represents the serialized form of {@code response}.
     */
    <T> AggregatedHttpResponse<HttpPayloadChunk> serialize(AggregatedHttpResponse<T> response,
                                                           BufferAllocator allocator);

    /**
     * Transforms the passed {@link AggregatedHttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a single {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param bytesEstimate An estimate as to how many bytes each serialization will require.
     * @param <T> The data type to serialize.
     *
     * @return An {@link AggregatedHttpResponse} which represents the serialized form of {@code response}.
     */
    <T> AggregatedHttpResponse<HttpPayloadChunk> serialize(AggregatedHttpResponse<T> response,
                                                           BufferAllocator allocator, int bytesEstimate);

    /**
     * Transforms the passed {@link HttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> HttpRequest<HttpPayloadChunk> serialize(HttpRequest<T> request, BufferAllocator allocator, Class<T> type);

    /**
     * Transforms the passed {@link HttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> HttpRequest<HttpPayloadChunk> serialize(HttpRequest<T> request, BufferAllocator allocator,
                                                TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link HttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * @param <T> The data type to serialize.
     *
     * size of the next object to be serialized in bytes.
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> HttpRequest<HttpPayloadChunk> serialize(HttpRequest<T> request, BufferAllocator allocator, Class<T> type,
                                                IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link HttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * size of the next object to be serialized in bytes.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> HttpRequest<HttpPayloadChunk> serialize(HttpRequest<T> request, BufferAllocator allocator,
                                                TypeHolder<T> typeHolder, IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link HttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> HttpResponse<HttpPayloadChunk> serialize(HttpResponse<T> response, BufferAllocator allocator, Class<T> type);

    /**
     * Transforms the passed {@link HttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> HttpResponse<HttpPayloadChunk> serialize(HttpResponse<T> response, BufferAllocator allocator,
                                                 TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link HttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * size of the next object to be serialized in bytes.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> HttpResponse<HttpPayloadChunk> serialize(HttpResponse<T> response, BufferAllocator allocator, Class<T> type,
                                                 IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link HttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * size of the next object to be serialized in bytes.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> HttpResponse<HttpPayloadChunk> serialize(HttpResponse<T> response, BufferAllocator allocator,
                                                 TypeHolder<T> typeHolder, IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link BlockingHttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> BlockingHttpRequest<HttpPayloadChunk> serialize(BlockingHttpRequest<T> request, BufferAllocator allocator,
                                                        Class<T> type);

    /**
     * Transforms the passed {@link BlockingHttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> BlockingHttpRequest<HttpPayloadChunk> serialize(BlockingHttpRequest<T> request, BufferAllocator allocator,
                                                        TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link BlockingHttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * size of the next object to be serialized in bytes.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> BlockingHttpRequest<HttpPayloadChunk> serialize(BlockingHttpRequest<T> request, BufferAllocator allocator,
                                                        Class<T> type, IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link BlockingHttpRequest} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param request The request which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * size of the next object to be serialized in bytes.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpRequest} which represents the serialized form of {@code request}.
     */
    <T> BlockingHttpRequest<HttpPayloadChunk> serialize(BlockingHttpRequest<T> request, BufferAllocator allocator,
                                                        TypeHolder<T> typeHolder, IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link BlockingHttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> BlockingHttpResponse<HttpPayloadChunk> serialize(BlockingHttpResponse<T> response, BufferAllocator allocator,
                                                         Class<T> type);

    /**
     * Transforms the passed {@link BlockingHttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> BlockingHttpResponse<HttpPayloadChunk> serialize(BlockingHttpResponse<T> response, BufferAllocator allocator,
                                                         TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link BlockingHttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * size of the next object to be serialized in bytes.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> BlockingHttpResponse<HttpPayloadChunk> serialize(BlockingHttpResponse<T> response, BufferAllocator allocator,
                                                         Class<T> type, IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link BlockingHttpResponse} such that the payload is serialized from {@code T} to
     * {@link HttpPayloadChunk}.
     *
     * @param response The response which contains a stream of {@code T}s.
     * @param allocator The {@link BufferAllocator} used to allocate {@link Buffer}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param bytesEstimator An {@link IntUnaryOperator} that given the last serialized size in bytes, estimates the
     * size of the next object to be serialized in bytes.
     * @param <T> The data type to serialize.
     *
     * @return An {@link HttpResponse} which represents the serialized form of {@code response}.
     */
    <T> BlockingHttpResponse<HttpPayloadChunk> serialize(BlockingHttpResponse<T> response, BufferAllocator allocator,
                                                         TypeHolder<T> typeHolder, IntUnaryOperator bytesEstimator);

    /**
     * Transforms the passed {@link AggregatedHttpRequest} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param request the request which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link AggregatedHttpRequest} which represents the deserialized form of {@code request}.
     *
     * @throws SerializationException If the request does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> AggregatedHttpRequest<T> deserialize(AggregatedHttpRequest<HttpPayloadChunk> request, Class<T> type);

    /**
     * Transforms the passed {@link AggregatedHttpRequest} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param request the request which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link AggregatedHttpRequest} which represents the deserialized form of {@code request}.
     *
     * @throws SerializationException If the request does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> AggregatedHttpRequest<T> deserialize(AggregatedHttpRequest<HttpPayloadChunk> request, TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link AggregatedHttpResponse} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param response the response which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link AggregatedHttpResponse} which represents the deserialized form of {@code response}.
     *
     * @throws SerializationException If the response does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> AggregatedHttpResponse<T> deserialize(AggregatedHttpResponse<HttpPayloadChunk> response, Class<T> type);

    /**
     * Transforms the passed {@link AggregatedHttpResponse} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param response the response which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link AggregatedHttpResponse} which represents the deserialized form of {@code response}.
     *
     * @throws SerializationException If the response does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> AggregatedHttpResponse<T> deserialize(AggregatedHttpResponse<HttpPayloadChunk> response,
                                              TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link HttpRequest} such that the payload is deserialized from {@link HttpPayloadChunk}
     * to {@code T}.
     *
     * @param request the request which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link HttpRequest} which represents the deserialized form of {@code request}.
     *
     * @throws SerializationException If the request does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> HttpRequest<T> deserialize(HttpRequest<HttpPayloadChunk> request, Class<T> type);

    /**
     * Transforms the passed {@link HttpRequest} such that the payload is deserialized from {@link HttpPayloadChunk}
     * to {@code T}.
     *
     * @param request the request which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link HttpRequest} which represents the deserialized form of {@code request}.
     *
     * @throws SerializationException If the request does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> HttpRequest<T> deserialize(HttpRequest<HttpPayloadChunk> request, TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link HttpResponse} such that the payload is deserialized from {@link HttpPayloadChunk}
     * to {@code T}.
     *
     * @param response the response which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link HttpResponse} which represents the deserialized form of {@code response}.
     *
     * @throws SerializationException If the response does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> HttpResponse<T> deserialize(HttpResponse<HttpPayloadChunk> response, Class<T> type);

    /**
     *
     * Transforms the passed {@link HttpResponse} such that the payload is deserialized from {@link HttpPayloadChunk}
     * to {@code T}.
     *
     * @param response the response which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link HttpResponse} which represents the deserialized form of {@code response}.
     *
     * @throws SerializationException If the response does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> HttpResponse<T> deserialize(HttpResponse<HttpPayloadChunk> response, TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link BlockingHttpRequest} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param request the request which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link BlockingHttpRequest} which represents the deserialized form of {@code request}.
     *
     * @throws SerializationException If the request does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> BlockingHttpRequest<T> deserialize(BlockingHttpRequest<HttpPayloadChunk> request, Class<T> type);

    /**
     * Transforms the passed {@link BlockingHttpRequest} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param request the request which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link BlockingHttpRequest} which represents the deserialized form of {@code request}.
     *
     * @throws SerializationException If the request does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> BlockingHttpRequest<T> deserialize(BlockingHttpRequest<HttpPayloadChunk> request, TypeHolder<T> typeHolder);

    /**
     * Transforms the passed {@link BlockingHttpResponse} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param response the response which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param type The class for {@link T}, the object to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link BlockingHttpResponse} which represents the deserialized form of {@code response}.
     *
     * @throws SerializationException If the response does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> BlockingHttpResponse<T> deserialize(BlockingHttpResponse<HttpPayloadChunk> response, Class<T> type);

    /**
     * Transforms the passed {@link BlockingHttpResponse} such that the payload is deserialized from
     * {@link HttpPayloadChunk} to {@code T}.
     *
     * @param response the response which contains a stream of encoded {@link HttpPayloadChunk}s.
     * @param typeHolder {@link TypeHolder} holding the {@link ParameterizedType} to be deserialized.
     * @param <T> The data type to deserialize.
     *
     * @return An {@link BlockingHttpResponse} which represents the deserialized form of {@code response}.
     *
     * @throws SerializationException If the response does not have the correct metadata indicating a payload that can be
     * deserialized.
     */
    <T> BlockingHttpResponse<T> deserialize(BlockingHttpResponse<HttpPayloadChunk> response, TypeHolder<T> typeHolder);
}
