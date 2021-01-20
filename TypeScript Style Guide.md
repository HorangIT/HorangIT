# TypeScript Style Guide 

> 기본 [JavaScript Style Guide(Airbnb)](https://github.com/tipjs/javascript-style-guide)에 TypeScript StyleGuide를 확장시켜 따릅니다.
>
> TypeScript StyleGuide는 [Deep Dive](https://basarat.gitbook.io/typescript/styleguide)를 참고했습니다. 

## Variable & Function
- 변수와 함수에는 `camelCase` 를 사용합니다.  

> 이유 : JavaScript 사용 방식입니다.

**Bad**

```ts
var FooVar;
function BarFunc() { }
```
**Good**

```ts
var fooVar;
function barFunc() { }
```

<br>

## Class

* class 이름에는 `PascalCase` 를 사용합니다.

> 이유: JavaScript 사용 방식입니다.

**Bad**
```ts
class foo { }
```
**Good**
```ts
class Foo { }
```
* class 멤버와 메소드에는 `camelCase` 를 사용합니다. 

> 이유: 변수와 함수 이름 컨벤션을 따라갑니다.

**Bad**

```ts
class Foo {
    Bar: number;
    Baz() { }
}
```
**Good**
```ts
class Foo {
    bar: number;
    baz() { }
}
```
<br>

## Type

* 이름은 `PascalCase` 를 사용합니다.

> 이유: class와 비슷합니다.

* 멤버는 `camelCase` 를 사용합니다.

> 이유: class와 비슷합니다.

<br>

## Interface

* 이름은 `PascalCase` 를 사용합니다.

> 이유: class와 비슷합니다.

* 멤버는 `camelCase` 를 사용합니다.

> 이유: class와 비슷합니다.

* `I` 를 맨 처음에 사용하지 **않습니다.**

>  이유: 비관습적입니다. `lib.d.ts` 는 중요한 인터페이스를  `I` 없이 정의합니다. (e.g. Window, Document etc).

**Bad**

```ts
interface IFoo {
}
```
**Good**

```ts
interface Foo {
}
```

<br>

## Type vs. Interface

* union이나 intersection이 꼭 필요할 때  `type` 을 사용하세요.

```
type Foo = number | { someProperty: number }
```

* `extends` 또는 `implements` 하고 싶을 때 `interface` 를 사용하세요. 

```
interface Foo {
  foo: string;
}
interface FooBar extends Foo {
  bar: string;
}
class X implements FooBar {
  foo: string;
  bar: string;
}
```

<br>


## Namespace

* 이름에는 `PascalCase` 를 사용합니다. 

> 이유:  TypeScript에서 정한 약속입니다. Namespace는 정적 멤버를 갖는 class 입니다. class이름은  `PascalCase` 이므로 Namespace 이름도 `PascalCase` 를 사용합니다.

**Bad**
```ts
namespace foo {
}
```
**Good**

```ts
namespace Foo {
}
```

<br>

## Enum

* 이름은 `PascalCase` 를 사용합니다. 

> 이유: class와 비슷합니다. Type에 해당합니다. 

**Bad**
```ts
enum color {
}
```
**Good**
```ts
enum Color {
}
```

* 멤버에는  `PascalCase` 를 사용합니다.

> 이유: 타입스크립트에서 정한 약속입니다. 이는 다른 언어를 타입스크립트로 번역(코드 생성)시 도움이 됩니다. (ex.  `SyntaxKind.StringLiteral`)

**Bad**

```ts
enum Color {
    red
}
```
**Good**

```ts
enum Color {
    Red
}
```

<br>

## Null vs. Undefined

* 명백하게 값이 없어도 **사용하지 않도록 합니다.**

> 이유: 이 값들은 값들 사이에 일관된 구조를 유지하기 위해 보통 사용됩니다. TypeScript에서는 `Type`으로 구조를 암시합니다.

**Bad**

```ts
let foo = { x: 123, y: undefined };
```
**Good**
```ts
let foo: { x: number, y?: number } = { x:123 };
```

* 일반적으로 `undefined` 를 사용합니다. 

  (대신 `{valid:boolean,value?:Foo}` 과 같은 객체를 return하는 것을 고려하세요.)

**Bad**

```ts
return null;
```
**Good**

```ts
return undefined;
```

* `null`은 API의 한 부분이거나, 관습적인 상황에서 사용합니다.

> 이유 : Node.js에서는 NodeBack 스타일 콜백에서 `error`를 `null` 로 하는 것이 관습적입니다.

**Bad**

```ts
cb(undefined)
```
**Good**

```ts
cb(null)
```

* `null`이나 `undefined` 값을 갖는 **객체**는 *truthy* 하게 검사하세요.

**Bad**

```ts
if (error === null)
```
**Good**
```ts
if (error)
```

* `null` / `undefined` 를 체크할 때 `== undefined` 또는 `!= undefined`를 사용하세요. (`===` / `!==` 말고)

  `null` / `undefined` 에는 작동하지만, 다른 fasly 값들(`''`,`0`,`false`) 에는 작동하지 않습니다.

**Bad**

```ts
if (error !== null) // does not rule out undefined
```
**Good**
```ts
if (error != null) // rules out both null and undefined
```

<br>

## Formatting

타입스크립트 컴파일러는 아주 좋은 언어 포메팅 서비스를 제공합니다. [`tsfmt`](https://github.com/vvakame/typescript-formatter) 를 사용하여 명령창에서 당신의 코드 형식을 자동으로 맞추세요. 또한 IDE  (atom/vscode/vs/sublime) 는 이미 포메팅 지원을 하고 있습니다.

```ts
// Space before type i.e. foo:<space>string
const foo: string = "hello";
```

<br>

## 따옴표

* 따옴표가 겹치는 상황이 아니라면 작은 따옴표(`'`)를 선호합니다.

> 이유 : 많은 JavaScript 팀들이 작은 따옴표를 사용합니다. (예: [airbnb](https://github.com/airbnb/javascript), [standard](https://github.com/feross/standard), [npm](https://github.com/npm/npm), [node](https://github.com/nodejs/node), [google/angular](https://github.com/angular/angular/), [facebook/react](https://github.com/facebook/react)). 
>
> 타이핑 하기가 더 쉽습니다. (대부분의 키보드에서 쉬프트를 누르지 않아도 됩니다). 

* 큰 따옴표를 사용할 수 없을 때, 백틱(`)을 사용해보세요.

> 이유 : 복잡한 문자열을 나타낼 때 일반적으로 사용합니다.

<br>

## Spaces

- 탭을 사용하지말고,  `2` 칸 띄어쓰기 합니다.

> 이유 : 많은 자바스크립트 팀이 이렇게 사용합니다.

<br>

## Array

* 배열은  `foos:Array<Foo>` 같은 형식 보다  `foos:Foo[]` 처럼 명시하세요

> 이유 : 읽기 더 쉽습니다.  `[]`를 잘 감지할 수 있어서 배열임을 알아차리기 더 쉬워집니다.

<br>

## 파일명
파일 이름은 `camelCase` 로 하세요. 예: `accordian.tsx`, `myControl.tsx`, `utils.ts`, `map.ts` 등등.

> 이유 : JavaScript 관습입니다.

<br>

