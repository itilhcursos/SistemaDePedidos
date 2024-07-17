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
.item {
  margin-top: 1rem;
  display: flex;
  position: relative;
}

.details {
  flex: 5;
  margin-left: -2rem;
}

i {
  display: flex;
  place-items: center;
  place-content: center;
  width: 100px;
  height: 100px;
  color: rgb(230, 31, 31);
}

h3 {
  font-size: 1.5rem;
  font-weight: 500;
  margin-bottom: 0.4rem;
  color: rgb(235, 14, 198);
}

@media (min-width: 1024px) {
  .item {
    margin-top: 0;
    padding: 1rem 0 1rem calc(var(--section-gap) / 2);
    color:  rgb(255, 254, 255);
  }

  i {
    top: calc(50% - 25px);
    left: -26px;
    position: absolute;
    border: 5px solid rgb(235, 66, 170);
    background: rgb(250, 239, 239);
    border-radius: 8px;
    width: 50px;
    height: 50px;
  }

  .item:before {
    content: ' ';
    border-left: 1px solid rgb(216, 126, 126);
    position: absolute;
    left: 0;
    bottom: calc(50% + 25px);
    height: calc(50% - 25px);
  }

  .item:after {
    content: ' ';
    border-left: 1px solid rgb(216, 126, 126);
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
