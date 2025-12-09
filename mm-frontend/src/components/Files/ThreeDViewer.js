import React from 'react';
import { Canvas } from '@react-three/fiber';
import { OrbitControls } from '@react-three/drei';
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
import { useLoader } from '@react-three/fiber';

const Model = ({ url }) => {
  const gltf = useLoader(GLTFLoader, url);
  return <primitive object={gltf.scene} />;
};

const ThreeDViewer = ({ fileUrl }) => (
  <div style={{ width: '400px', height: '400px' }}>
    <Canvas>
      <ambientLight intensity={0.5} />
      <pointLight position={[10, 10, 10]} />
      <Model url={fileUrl} />
      <OrbitControls />
    </Canvas>
  </div>
);

export default ThreeDViewer;
