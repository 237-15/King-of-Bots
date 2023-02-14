<template>
	<div id="base-map">
		<canvas id="renderCanvas"></canvas>
	</div>
</template>

<script>
import * as DigitalSand from 'DigitalSand';
export default {
	name: 'BaseMap',
	data() {
		return {

		}
	},
	mounted() {
		this.initBabylonScene();
	},
	methods: {
		initBabylonScene() {
			const canvas = document.getElementById("renderCanvas");
			const mainViewer = new DigitalSand.MainViewer(canvas);
			mainViewer.Run();
		}
	}
}
</script>

<style scoped lang="scss">
#base-map {
	position: absolute;
	top: 0;
	left: 0;
	z-index: 0;
	width: $mapWidth;
	height: $mapHeight;
	#renderCanvas {
		width: $mapWidth;
		height: $mapHeight;
	}
}
</style>
