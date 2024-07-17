<template>
  <div class="item">
    <i>
      <slot name="icon"></slot>
    </i>
    <div class="details">
      <h3>
        <slot name="heading"></slot>
      </h3>
      <slot></slot>
    </div>
  </div>
</template>

<style scoped>

:root {
  --cor0: #c8d1f2;
  --cor1: #0B1654;
  --cor2: #33395D;
  --cor3: #8591D4;
  --cor4: #AE91D4;
  --cor5: #d65f71;
  
  --fonte-padrao: Arial, Verdana, Helvetica, sans-serif;
  --fonte-destaque: 'Bebas Neue', sans-serif;
}

h3 {
  background-color: #33395D;
  margin-top: 10px;
  border-radius: 5px;
  text-shadow: 2px 2px 0px rgba(0, 0, 0, 0.267);
  text-align: left;
  padding: 6px;
  color: #d65f71;
}

@media (min-width: 1024px) {
  .item {
    margin-top: 0;
    padding: 0.4rem 0 1rem calc(var(--section-gap) / 2);
  }

  i {
    top: calc(50% - 25px);
    left: -26px;
    position: absolute;
    border: 1px solid var(--color-border);
    background: var(--color-background);
    border-radius: 8px;
    width: 50px;
    height: 50px;
  }

  .item:before {
    content: ' ';
    border-left: 1px solid var(--color-border);
    position: absolute;
    left: 0;
    bottom: calc(50% + 25px);
    height: calc(50% - 25px);
  }

  .item:after {
    content: ' ';
    border-left: 1px solid var(--color-border);
    position: absolute;
    left: 0;
    top: calc(50% + 25px);
    height: calc(50% - 25px);
  }

  .item:first-of-type:before {
    display: none;
  }

  .item:last-of-type:after {
    display: none;
  }
}
</style>
